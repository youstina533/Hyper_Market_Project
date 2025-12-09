package hypermarket2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the UI components
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

        Label idLabel = new Label("ID:");
        TextField idField = new TextField();

        Label roleLabel = new Label("Role:");
        TextField roleField = new TextField();  // User role field (e.g., admin, seller)

        Button loginButton = new Button("Login");

        // Add action for the login button
        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText(), roleField.getText(), primaryStage));

        // Layout the components in a vertical box
        VBox layout = new VBox(10, usernameLabel, usernameField, passwordLabel, passwordField, nameLabel, nameField, idLabel, idField, roleLabel, roleField, loginButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px; -fx-background-color: lightblue;");

        // Create and set the scene
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleLogin(String username, String password, String role, Stage primaryStage) {
        if (isValidCredentials(username, password, role)) {
            // Check role and redirect to the appropriate module
            switch (role.toLowerCase()) {
                case "admin":
                  AdminModuleGUI adminGUI = new AdminModuleGUI();
                  adminGUI.start(primaryStage); 
                    break;
                case "seller":
                    SellerModuleGUI sellerGUI = new SellerModuleGUI();
                    sellerGUI.start(primaryStage); // Assuming SellerModuleGUI is a separate class
                    break;
                case "inventory admin":
                    InventoryAdminModuleGUI inventoryAdminGUI = new InventoryAdminModuleGUI();
                    inventoryAdminGUI.start(primaryStage); // Assuming InventoryAdminModuleGUI is a separate class
                    break;
                case "marketing man":
                    MarketingmanModuleGUI marketingManGUI = new MarketingmanModuleGUI();
                    marketingManGUI.start(primaryStage); // Assuming MarketingmanModuleGUI is a separate class
                    break;
                default:
                    showError("Invalid Role");
                    break;
            }
        } else {
            showError("Invalid Username or Password");
        }
    }

    private boolean isValidCredentials(String username, String password, String role) {
        if (username.equals("admin") && password.equals("password") && role.equals("admin")) {
            return true;
        } else if (username.equals("seller") && password.equals("password") && role.equals("seller")) {
            return true;
        } else if (username.equals("inventory") && password.equals("password") && role.equals("inventory admin")) {
            return true;
        } else if (username.equals("marketing") && password.equals("password") && role.equals("marketing man")) { 
            return true;
        } else {
            return false; 
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
