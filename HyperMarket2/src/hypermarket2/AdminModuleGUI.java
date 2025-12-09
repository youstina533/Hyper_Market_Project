package hypermarket2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class AdminModuleGUI extends Application {

    private AdminModule adminModule = new AdminModule(); // Instance of your AdminModule class

    @Override
    public void start(Stage primaryStage) {
        // Admin Dashboard
        Button addButton = new Button("Add Employee");
        Button deleteButton = new Button("Delete Employee");
        Button updateButton = new Button("Update Employee");
        Button listButton = new Button("List All Employees");
        Button searchButton = new Button("Search Employee");
        Button changeCredsButton = new Button("Change Admin Credentials"); // New button for changing credentials

        VBox adminLayout = new VBox(10, addButton, deleteButton, updateButton, listButton, searchButton, changeCredsButton);
        adminLayout.setPadding(new Insets(20));

        Scene adminScene = new Scene(adminLayout, 400, 350);

        // Button Actions
        addButton.setOnAction(e -> showAddEmployeeWindow());
        deleteButton.setOnAction(e -> showDeleteEmployeeWindow());
        updateButton.setOnAction(e -> showUpdateEmployeeWindow());
        listButton.setOnAction(e -> listAllEmployees());
        searchButton.setOnAction(e -> searchEmployee());
        changeCredsButton.setOnAction(e -> showChangeAdminCredentialsWindow()); // Action for Change Credentials

        primaryStage.setTitle("Admin Dashboard");
        primaryStage.setScene(adminScene);
        primaryStage.show();
    }

    private void showAddEmployeeWindow() {
        Stage addStage = new Stage();
        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField typeField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button saveButton = new Button("Save");

        VBox layout = new VBox(10, new Label("ID:"), idField, new Label("Name:"), nameField, new Label("Type:"), typeField,
                new Label("Password:"), passwordField, saveButton);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 300, 300);

        saveButton.setOnAction(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String type = typeField.getText();
            String password = passwordField.getText();
            try {
                Scanner scanner = new Scanner(System.in);
                System.setIn(new ByteArrayInputStream((id + "\n" + name + "\n" + type + "\n" + password + "\n").getBytes()));
                AdminModule.addEmployee(scanner); // Reuse method from AdminModule
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Employee added successfully!");
                alert.show();
                addStage.close();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage());
                alert.show();
            }
        });

        addStage.setTitle("Add Employee");
        addStage.setScene(scene);
        addStage.show();
    }

    private void showDeleteEmployeeWindow() {
        Stage deleteStage = new Stage();
        TextField idField = new TextField();
        Button deleteButton = new Button("Delete");

        VBox layout = new VBox(10, new Label("Enter Employee ID to delete:"), idField, deleteButton);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 300, 150);

        deleteButton.setOnAction(e -> {
            String id = idField.getText();
            try {
                Scanner scanner = new Scanner(System.in);
                System.setIn(new ByteArrayInputStream((id + "\n").getBytes()));
                AdminModule.deleteEmployee(scanner); // Reuse method from AdminModule
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Employee deleted if found!");
                alert.show();
                deleteStage.close();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage());
                alert.show();
            }
        });

        deleteStage.setTitle("Delete Employee");
        deleteStage.setScene(scene);
        deleteStage.show();
    }

    private void showUpdateEmployeeWindow() {
        // Update Employee Window
        Stage updateStage = new Stage();

        // Fields for ID, Name, Type, and Password
        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField typeField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button updateButton = new Button("Update");

        VBox layout = new VBox(10, new Label("Enter Employee ID to update:"), idField, 
                              new Label("New Name:"), nameField, 
                              new Label("New Type:"), typeField,
                              new Label("New Password:"), passwordField, updateButton);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 300, 300);

        updateButton.setOnAction(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String type = typeField.getText();
            String password = passwordField.getText();

            try {
                // Use AdminModule.updateEmployee() or similar method to update employee details
                Scanner scanner = new Scanner(System.in);
                System.setIn(new ByteArrayInputStream((id + "\n" + name + "\n" + type + "\n" + password + "\n").getBytes()));
                AdminModule.updateEmployee(scanner); // Reuse method from AdminModule
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Employee updated successfully!");
                alert.show();
                updateStage.close();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage());
                alert.show();
            }
        });

        updateStage.setTitle("Update Employee");
        updateStage.setScene(scene);
        updateStage.show();
    }

    private void listAllEmployees() {
        StringBuilder output = new StringBuilder("Employees:\n");
        try (BufferedReader reader = new BufferedReader(new FileReader(AdminModule.EMPLOYEE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, output.toString());
            alert.setHeaderText("All Employees");
            alert.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error reading file: " + e.getMessage());
            alert.show();
        }
    }

    private void searchEmployee() {
        Stage searchStage = new Stage();
        TextField idField = new TextField();
        Button searchButton = new Button("Search");

        VBox layout = new VBox(10, new Label("Enter Employee ID to search:"), idField, searchButton);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 300, 150);

        searchButton.setOnAction(e -> {
            String id = idField.getText();
            try (BufferedReader reader = new BufferedReader(new FileReader(AdminModule.EMPLOYEE_FILE))) {
                String line;
                boolean found = false;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts[0].equals(id)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "ID: " + parts[0] + ", Name: " + parts[1] + ", Type: " + parts[2]);
                        alert.show();
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Employee not found.");
                    alert.show();
                }
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage());
                alert.show();
            }
        });

        searchStage.setTitle("Search Employee");
        searchStage.setScene(scene);
        searchStage.show();
    }

    private void showChangeAdminCredentialsWindow() {
        Stage changeCredsStage = new Stage();
        TextField usernameField = new TextField();
        PasswordField newPasswordField = new PasswordField();
        PasswordField confirmPasswordField = new PasswordField();
        Button saveButton = new Button("Save");

        VBox layout = new VBox(10, new Label("New Username:"), usernameField, 
                              new Label("New Password:"), newPasswordField, 
                              new Label("Confirm Password:"), confirmPasswordField, saveButton);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 300, 300);

        saveButton.setOnAction(e -> {
            String username = usernameField.getText();
            String newPassword = newPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (newPassword.equals(confirmPassword)) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(AdminModule.CREDENTIALS_FILE))) {
                    writer.write(username + "," + newPassword);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Admin credentials updated successfully!");
                    alert.show();
                    changeCredsStage.close();
                } catch (IOException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage());
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Passwords do not match.");
                alert.show();
            }
        });

        changeCredsStage.setTitle("Change Admin Credentials");
        changeCredsStage.setScene(scene);
        changeCredsStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
