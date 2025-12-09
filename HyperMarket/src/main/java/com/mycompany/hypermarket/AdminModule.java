
//package com.mycompany.hypermarket;
import java.io.*;
import java.util.*;

public class AdminModule extends User{
    private static final String EMPLOYEE_FILE = "employees.txt";

    // Admin credentials
    private static String adminUsername = "admin";
    private static String adminPassword = "password";
    private static boolean authenticateAdmin(String username, String password) {
        return adminUsername.equals(username) && adminPassword.equals(password);
    }

    private static void changeAdminCredentials(Scanner scanner) {
        System.out.print("Enter new Admin Username: ");
        adminUsername = scanner.nextLine();
        System.out.print("Enter new Admin Password: ");
        adminPassword = scanner.nextLine();
        System.out.println("Admin credentials updated successfully!");
    }

    private static void addEmployee(Scanner scanner) {
        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEE_FILE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(EMPLOYEE_FILE, true))) {
            
            Set<String> existingIds = new HashSet<>();
            String line;

            // Read all existing IDs from the file
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    existingIds.add(parts[0]); // Add ID to the set
                }
            }

            System.out.print("Enter Employee ID: ");
            String id = scanner.nextLine();

            // Check if ID is already in use
            if (existingIds.contains(id)) {
                System.out.println("Error: Employee ID already exists. Please try again.");
                return;
                 }

            System.out.print("Enter Employee Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Employee Type: ");
            String type = scanner.nextLine();
            System.out.print("Enter Employee Password: ");
            String password = scanner.nextLine();

            // Write the new employee record to the file
            writer.write(id + "," + name + "," + type + "," + password);
            writer.newLine();
            System.out.println("Employee added successfully!");

        } catch (IOException e) {
            System.out.println("Error handling file: " + e.getMessage());
        }
    }


    private static void deleteEmployee(Scanner scanner) {
        System.out.print("Enter Employee ID to delete: ");
        String idToDelete = scanner.nextLine();

        File inputFile = new File(EMPLOYEE_FILE);
        File tempFile = new File("temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean found = false;
               while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (!parts[0].equals(idToDelete)) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    found = true;
                }
            }

            if (found) {
                System.out.println("Employee deleted successfully!");
            } else {
                System.out.println("Employee not found.");
            }

        } catch (IOException e) {
            System.out.println("Error handling file: " + e.getMessage());
        }

        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            System.out.println("Error updating file.");
        }
    }

    private static void updateEmployee(Scanner scanner) {
        System.out.print("Enter Employee ID to update: ");
        String idToUpdate = scanner.nextLine();

        File inputFile = new File(EMPLOYEE_FILE);
        File tempFile = new File("temp.txt");
         try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(idToUpdate)) {
                    found = true;
                    System.out.print("Enter new Employee Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter new Employee Type: ");
                    String type = scanner.nextLine();
                    System.out.print("Enter new Employee Password: ");
                    String password = scanner.nextLine();

                    writer.write(idToUpdate + "," + name + "," + type + "," + password);
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }

            if (found) {
                System.out.println("Employee updated successfully!");
            } else {
                System.out.println("Employee not found.");
            }

        } catch (IOException e) {
            System.out.println("Error handling file: " + e.getMessage());
        }
    if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            System.out.println("Error updating file.");
        }
    }

    private static void listAllEmployees() {
        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEE_FILE))) {
            String line;
            System.out.println("\nEmployees:");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                System.out.println("ID: " + parts[0] + ", Name: " + parts[1] + ", Type: " + parts[2]);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void searchEmployee(Scanner scanner) {
        System.out.print("Enter Employee ID to search: ");
        String idToSearch = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEE_FILE))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(idToSearch)) {
                    System.out.println("ID: " + parts[0] + ", Name: " + parts[1] + ", Type: " + parts[2]);
                    found = true;
                    break;
                }
            }
              if (!found) {
                System.out.println("Employee not found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}             
