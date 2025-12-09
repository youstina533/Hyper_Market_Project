package hypermarket2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MarketingmanModuleGUI extends Application {

    private final String PRODUCT_FILE = "products.txt"; // Ensure your file name is products.txt for product data
    private List<Product> products;

    @Override
    public void start(Stage primaryStage) {
        // Load products from file
        products = loadProductsFromFile();

        // Layout
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Buttons for operations
        Button generateReportBtn = new Button("Generate Product Report");
        Button createOfferBtn = new Button("Create Special Offer");
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(200);

        // Event handlers
        generateReportBtn.setOnAction(e -> {
            outputArea.setText(generateProductReport());
        });

        createOfferBtn.setOnAction(e -> {
            Dialog<Pair<Integer, Double>> dialog = new Dialog<>();
            dialog.setTitle("Create Special Offer");
            dialog.setHeaderText("Enter Product ID and Discount Percentage");

            // Input fields
            TextField productIdField = new TextField();
            productIdField.setPromptText("Product ID");
            TextField discountField = new TextField();
            discountField.setPromptText("Discount (%)");

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.add(new Label("Product ID:"), 0, 0);
            grid.add(productIdField, 1, 0);
            grid.add(new Label("Discount (%):"), 0, 1);
            grid.add(discountField, 1, 1);
            dialog.getDialogPane().setContent(grid);

            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    try {
                        int productId = Integer.parseInt(productIdField.getText());
                        double discount = Double.parseDouble(discountField.getText());
                        return new Pair<>(productId, discount);
                    } catch (NumberFormatException ex) {
                        return null;
                    }
                }
                return null;
            });

            dialog.showAndWait().ifPresent(pair -> {
                int productId = pair.getKey();
                double discount = pair.getValue();
                outputArea.setText(createSpecialOffer(productId, discount));
            });
        });

        // Add components to layout
        layout.getChildren().addAll(generateReportBtn, createOfferBtn, outputArea);

        // Scene
        Scene scene = new Scene(layout, 500, 400);
        primaryStage.setTitle("Marketing Module");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper Methods
    private List<Product> loadProductsFromFile() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                products.add(new Product(
                        Integer.parseInt(parts[0]), // ID
                        parts[1],                  // Name
                        Double.parseDouble(parts[2]), // Price
                        Double.parseDouble(parts[3]), // Offer Price
                        Integer.parseInt(parts[4]), // Quantity
                        parts[5]                   // Expire Date
                ));
            }
        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
        return products;
    }

    private String generateProductReport() {
        StringBuilder report = new StringBuilder();
        report.append(String.format("%-10s %-20s %-10s %-10s\n", "ID", "Name", "Price", "Quantity"));
        for (Product product : products) {
            report.append(String.format("%-10d %-20s %-10.2f %-10d\n",
                    product.getId(), product.getName(), product.getPrice(), product.getQuantity()));
        }
        return report.toString();
    }

    private String createSpecialOffer(int productId, double discountPercentage) {
        Product product = getProductById(productId);
        if (product == null) {
            return "Product not found!";
        }
        double discountedPrice = product.getPrice() - (product.getPrice() * (discountPercentage / 100));
        product.setOfferPrice(discountedPrice);
        updateProductFile(product);
        return "Special offer applied to " + product.getName() + " successfully!";
    }

    private Product getProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    private void updateProductFile(Product updatedProduct) {
        File inputFile = new File(PRODUCT_FILE);
        File tempFile = new File("temp_products.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0]) == updatedProduct.getId()) {
                    writer.write(updatedProduct.getId() + "," + updatedProduct.getName() + "," +
                            updatedProduct.getPrice() + "," + updatedProduct.getOfferPrice() + "," +
                            updatedProduct.getQuantity() + "," + updatedProduct.getExpireDate());
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating product file: " + e.getMessage());
        }

        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            System.out.println("Error updating product file.");
        }
    }

}
