package com.mycompany.hypermarket;
import java.io.*;
import java.util.*;

public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private String role; 
   public User(){
       
   }
    public User(int id, String name, String username, String password, String role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }
 public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void logout() {
        System.out.println("User " + name + " has logged out.");
    }

    public void displayUserInfo() {
        System.out.println("User ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Username: " + username);
        System.out.println("Role: " + role);
    }
     public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("employees.txt", true))) { 
            writer.write("ID: " + id + ", Name: " + name + ", Username: " + username + ", Role: " + role + "\n");
            writer.flush();
            System.out.println("User information saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving user to file: " + e.getMessage());
        }
    }

    public void updateUserInfo(Scanner scanner) {
        System.out.print("Enter new name (Leave empty to keep current): ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            setName(newName);
        }

        System.out.print("Enter new username (Leave empty to keep current): ");
        String newUsername = scanner.nextLine();
        if (!newUsername.isEmpty()) {
            setUsername(newUsername);
        }

        System.out.print("Enter new password (Leave empty to keep current): ");
        String newPassword = scanner.nextLine();
        if (!newPassword.isEmpty()) {
            setPassword(newPassword);
        }

        System.out.print("Enter new role (Leave empty to keep current): ");
        String newRole = scanner.nextLine();
        if (!newRole.isEmpty()) {
            setRole(newRole);
        }
  System.out.println("User information updated successfully!");
    }

    public void logAction(String action) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("employee_actions.txt", true))) { 
            writer.write("User ID: " + id + ", Role: " + role + ", Action: " + action + "\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error logging action: " + e.getMessage());
        }
    }

    public void viewActionHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader("employee_actions.txt"))) { 
            StringBuilder history = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("User ID: " + id)) { 
                    history.append(line).append("\n");
                }
            }
            System.out.println("Action History:\n" + history.toString());
        } catch (IOException e) {
            System.out.println("Error reading action history: " + e.getMessage());
        }
    }
}

