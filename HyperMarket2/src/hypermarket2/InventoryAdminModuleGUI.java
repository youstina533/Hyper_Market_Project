package hypermarket2;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class InventoryAdminModuleGUI extends Application {
    private Inventory inventory;
    private TextField productIdField, productNameField, productPriceField, productQuantityField, productExpireDateField, offerPriceField;
    private TextArea productDisplayArea;

    @Override
    public void start(Stage primaryStage) {
        inventory = new Inventory(); // Initialize the Inventory instance

        // Set up the UI components
        productIdField = new TextField();
        productNameField = new TextField();
        productPriceField = new TextField();
        productQuantityField = new TextField();
        productExpireDateField = new TextField();
        offerPriceField = new TextField();
        productDisplayArea = new TextArea();
        productDisplayArea.setEditable(false);

        // Buttons for different operations
        Button addButton = new Button("Add Product");
        Button deleteButton = new Button("Delete Product");
        Button updateButton = new Button("Update Product");
        Button listButton = new Button("List Products");
        Button returnButton = new Button("Sales Return");
        Button damageButton = new Button("Mark Damaged");

        // Set actions for buttons
        addButton.setOnAction(event -> addProduct());
        deleteButton.setOnAction(event -> deleteProduct());
        updateButton.setOnAction(event -> updateProduct());
        listButton.setOnAction(event -> listProducts());
        returnButton.setOnAction(event -> handleSalesReturn());
        damageButton.setOnAction(event -> handleDamagedItems());
        // Layout setup
        VBox inputBox = new VBox(10, new Label("Product ID:"), productIdField, new Label("Name:"), productNameField, 
                                new Label("Price:"), productPriceField, new Label("Quantity:"), productQuantityField, 
                                new Label("Expire Date:"), productExpireDateField, new Label("Offer Price (optional):"), offerPriceField);
        inputBox.setPadding(new javafx.geometry.Insets(10));

        VBox buttonBox = new VBox(10, addButton, deleteButton, updateButton, listButton, returnButton, damageButton);
        buttonBox.setPadding(new javafx.geometry.Insets(10));

        // Display Area for Product Information
        VBox displayBox = new VBox(10, new Label("Product Information:"), productDisplayArea);
        displayBox.setPadding(new javafx.geometry.Insets(10));

        // Main layout
        HBox mainLayout = new HBox(20, inputBox, buttonBox, displayBox);

        // Scene setup
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setTitle("Inventory Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Add Product
    private void addProduct() {
        try {
            String name = productNameField.getText();
            double price = Double.parseDouble(productPriceField.getText());
            
            String expireDate = productExpireDateField.getText();
            String type = productNameField.getText();

            Product product = new Product(name, price, expireDate, type);
            inventory.addProduct(product);
            productDisplayArea.setText("Product added successfully!");

        } catch (Exception e) {
            productDisplayArea.setText("Error adding product: " + e.getMessage());
        }
    }

    // Delete Product
    private void deleteProduct() {
        try {
            int productId = Integer.parseInt(productIdField.getText());
            inventory.deleteProduct(productId);
            productDisplayArea.setText("Product deleted successfully!");
        } catch (Exception e) {
            productDisplayArea.setText("Error deleting product: " + e.getMessage());
        }
    }

    // Update Product
    private void updateProduct() {
        try {
            int productId = Integer.parseInt(productIdField.getText());
            Product updatedProduct = new Product(productId, productNameField.getText(), 
                Double.parseDouble(productPriceField.getText()), Integer.parseInt(productQuantityField.getText()), 
                productExpireDateField.getText());
            inventory.updateProduct(productId, updatedProduct);
            productDisplayArea.setText("Product updated successfully!");
        } catch (Exception e) {
            productDisplayArea.setText("Error updating product: " + e.getMessage());
        }
    }

    // List Products
    private void listProducts() {
        List<Product> products = inventory.listProducts();
        StringBuilder displayText = new StringBuilder("Product List:\n");
        for (Product product : products) {
            displayText.append(product.toString()).append("\n");
        }
        productDisplayArea.setText(displayText.toString());
    }

    // Handle Sales Return
    private void handleSalesReturn() {
        try {
            int productId = Integer.parseInt(productIdField.getText());
            int quantity = Integer.parseInt(productQuantityField.getText());
            inventory.handleSalesReturn(productId, quantity);
            productDisplayArea.setText("Sales return processed successfully!");
        } catch (Exception e) {
            productDisplayArea.setText("Error handling sales return: " + e.getMessage());
        }
    }

    // Handle Damaged Items
    private void handleDamagedItems() {
        try {
            int productId = Integer.parseInt(productIdField.getText());
            int quantity = Integer.parseInt(productQuantityField.getText());
            inventory.damagedItems(productId, quantity);
            productDisplayArea.setText("Damaged items processed successfully!");
        } catch (Exception e) {
            productDisplayArea.setText("Error processing damaged items: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
