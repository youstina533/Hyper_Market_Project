package hypermarket2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.io.*;
import java.util.Scanner;

public class SellerModuleGUI extends Application {

    private final String PRODUCT_FILE = "products.txt";
    private final String ORDER_FILE = "orders.txt";

    @Override
    public void start(Stage primaryStage) {
        // Layout
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Buttons for operations
        Button listProductsBtn = new Button("List All Products");
        Button searchProductBtn = new Button("Search Product");
        Button createOrderBtn = new Button("Create Order");
        Button cancelOrderBtn = new Button("Cancel Order");
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(200);

        // Event handlers for buttons
        listProductsBtn.setOnAction(e -> {
            outputArea.setText(listAllProducts());
        });

        searchProductBtn.setOnAction(e -> {
            TextInputDialog inputDialog = new TextInputDialog();
            inputDialog.setTitle("Search Product");
            inputDialog.setHeaderText("Enter Product ID:");
            inputDialog.showAndWait().ifPresent(productId -> {
                outputArea.setText(searchProduct(productId));
            });
        });

        createOrderBtn.setOnAction(e -> {
            Dialog<Pair<String, Integer>> dialog = new Dialog<>();
            dialog.setTitle("Create Order");
            dialog.setHeaderText("Enter Product ID and Quantity");

            // Fields for input
            TextField productIdField = new TextField();
            productIdField.setPromptText("Product ID");
            TextField quantityField = new TextField();
            quantityField.setPromptText("Quantity");

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.add(new Label("Product ID:"), 0, 0);
            grid.add(productIdField, 1, 0);
            grid.add(new Label("Quantity:"), 0, 1);
            grid.add(quantityField, 1, 1);
            dialog.getDialogPane().setContent(grid);

            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    try {
                        int quantity = Integer.parseInt(quantityField.getText());
                        return new Pair<>(productIdField.getText(), quantity);
                    } catch (NumberFormatException ex) {
                        return null;
                    }
                }
                return null;
            });

            dialog.showAndWait().ifPresent(pair -> {
                String productId = pair.getKey();
                int quantity = pair.getValue();
                outputArea.setText(createOrder(productId, quantity));
            });
        });

        cancelOrderBtn.setOnAction(e -> {
            TextInputDialog inputDialog = new TextInputDialog();
            inputDialog.setTitle("Cancel Order");
            inputDialog.setHeaderText("Enter Product ID to Cancel Order:");
            inputDialog.showAndWait().ifPresent(productId -> {
                outputArea.setText(cancelOrder(productId));
            });
        });

        // Add components to layout
        layout.getChildren().addAll(
                listProductsBtn, searchProductBtn, createOrderBtn, cancelOrderBtn, outputArea
        );

        // Scene
        Scene scene = new Scene(layout, 500, 400);
        primaryStage.setTitle("Seller Module");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper Methods
    private String listAllProducts() {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                result.append("ID: ").append(parts[0])
                        .append(", Name: ").append(parts[1])
                        .append(", Price: ").append(parts[2]).append("\n");
            }
        } catch (IOException e) {
            result.append("Error reading product file: ").append(e.getMessage());
        }
        return result.toString();
    }

    private String searchProduct(String productId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(productId)) {
                    return "Product Found: ID: " + parts[0] + ", Name: " + parts[1] + ", Price: " + parts[2];
                }
            }
        } catch (IOException e) {
            return "Error reading product file: " + e.getMessage();
        }
        return "Product not found.";
    }


    private String createOrder(String productId, int quantity) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(ORDER_FILE, true))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(productId)) {
                    writer.write(productId + "," + parts[1] + "," + quantity + "," + parts[2]);
                    writer.newLine();
                    return "Order created successfully for Product: " + parts[1];
                }
            }
        } catch (IOException e) {
            return "Error handling files: " + e.getMessage();
        }
        return "Product not found. Order not created.";
    }

    private String cancelOrder(String productId) {
        File inputFile = new File(ORDER_FILE);
        File tempFile = new File("temp_orders.txt");
        StringBuilder result = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (!parts[0].equals(productId)) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    found = true;
                }
            }

            if (found) {
                result.append("Order canceled successfully!");
            } else {
                result.append("Order not found.");
            }
        } catch (IOException e) {
            return "Error handling files: " + e.getMessage();
        }

        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            result.append("\nError updating order file.");
        }
        return result.toString();
    }

    public static void main(String[] args) {
        launch(args);
    }
}