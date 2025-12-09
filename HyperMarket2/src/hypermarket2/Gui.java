
//package hypermarket2;
//
//import javafx.application.Application;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//public class Gui extends Application {
//    @Override
//    public void start(Stage primaryStage) {
//        // Create the UI components
//        Label usernameLabel = new Label("Username:");
//        TextField usernameField = new TextField();
//        
//        Label passwordLabel = new Label("Password:");
//        PasswordField passwordField = new PasswordField();
//
//        Label nameLabel = new Label("Name:");
//        TextField nameField = new TextField();
//
//        Label idLabel = new Label("ID:");
//        TextField idField = new TextField();
//
//        Label roleLabel = new Label("Role:");
//        TextField roleField = new TextField();  // User role field (e.g., admin, seller)
//
//        Button loginButton = new Button("Login");
//
//        // Add action for the login button
//        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText(), roleField.getText(), primaryStage));
//
//        // Layout the components in a vertical box
//        VBox layout = new VBox(10, usernameLabel, usernameField, passwordLabel, passwordField, nameLabel, nameField, idLabel, idField, roleLabel, roleField, loginButton);
//        layout.setAlignment(Pos.CENTER);
//        layout.setStyle("-fx-padding: 20px; -fx-background-color: lightblue;");
//
//        // Create and set the scene
//        Scene scene = new Scene(layout, 300, 250);
//        primaryStage.setTitle("Login");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    // This method will be triggered when the login button is clicked
//    private void handleLogin(String username, String password, String role, Stage primaryStage) {
//        // Simulating the user authentication process
//        if (username.equals("admin") && password.equals("password") && role.equals("admin")) {
//            // If the role is admin, go to the Admin page
//            AdminModuleGUI adminGUI = new AdminModuleGUI();
//            adminGUI.start(primaryStage); // Switch to Admin GUI
//        } else if (username.equals("seller") && password.equals("password") && role.equals("seller")) {
//            // If the role is seller, go to the Seller page (Implement SellerModuleGUI separately)
//            SellerModuleGUI sellerGUI = new SellerModuleGUI();
//            sellerGUI.start(primaryStage); // Switch to Seller GUI
//        } else if (username.equals("inventory_admin") && password.equals("password") && role.equals("inventory admin")) {
//            // If the role is inventory admin, go to Inventory Admin page
//            InventoryAdminModuleGUI inventoryAdminGUI = new InventoryAdminModuleGUI();
//            inventoryAdminGUI.start(primaryStage); // Switch to Inventory Admin GUI
//        } else if (username.equals("marketing_man") && password.equals("password") && role.equals("marketing man")) {
//            // If the role is marketing man, go to Marketing Man page
//            MarketingmanModuleGUI marketingManGUI = new MarketingmanModuleGUI();
//            marketingManGUI.start(primaryStage); // Switch to Marketing Man GUI
//        } else {
//            // Show error if credentials are incorrect
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Login Error");
//            alert.setHeaderText(null);
//            alert.setContentText("Invalid username, password, or role.");
//            alert.showAndWait();
//        }
//    }
//}
