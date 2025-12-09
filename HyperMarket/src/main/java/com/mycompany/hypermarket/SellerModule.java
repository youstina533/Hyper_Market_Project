//package com.mycompany.hypermarket;
import java.io.*;
import java.util.*;

public class SellerModule extends User{
    private static final String PRODUCT_FILE = "products.txt";
    private static final String ORDER_FILE = "orders.txt";
 private static void searchProduct(Scanner scanner) {
        System.out.print("Enter Product ID to search: ");
        String productId = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(productId)) {
                    System.out.println("Product Found: ID: " + parts[0] + ", Name: " + parts[1] + ", Price: " + parts[2]);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Product not found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading product file: " + e.getMessage());
        }
    }

    private static void listAllProducts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            System.out.println("\nAvailable Products:");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                System.out.println("ID: " + parts[0] + ", Name: " + parts[1] + ", Price: " + parts[2]);
            }
        } catch (IOException e) {
            System.out.println("Error reading product file: " + e.getMessage());
        }
         }

    private static void createOrder(Scanner scanner) {
        System.out.print("Enter Product ID to order: ");
        String productId = scanner.nextLine();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(ORDER_FILE, true))) {

            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(productId)) {
                    found = true;
                    writer.write(productId + "," + parts[1] + "," + quantity + "," + parts[2]);
                    writer.newLine();
                    System.out.println("Order created successfully for Product: " + parts[1]);
                    break;
                }
            }

            if (!found) {
                System.out.println("Product not found. Order not created.");
            }
        } catch (IOException e) {
            System.out.println("Error handling files: " + e.getMessage());
        }
    }
     private static void cancelOrder(Scanner scanner) {
        System.out.print("Enter Product ID to cancel order: ");
        String productId = scanner.nextLine();

        File inputFile = new File(ORDER_FILE);
        File tempFile = new File("temp_orders.txt");

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
                System.out.println("Order canceled successfully!");
            } else {
                System.out.println("Order not found.");
            }

        } catch (IOException e) {
            System.out.println("Error handling files: " + e.getMessage());
        }
      if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            System.out.println("Error updating order file.");
        }
    }
}            