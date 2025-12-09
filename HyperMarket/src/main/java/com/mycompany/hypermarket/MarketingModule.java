
//package com.mycompany.hypermarket;
//import java.io.*;
//import java.util.Scanner;
//public class MarketingModule extends User {
//    private static final String OFFERS_FILE = "employees.txt"; 
//    private Inventory inventory;
//
//    public MarketingModule(Inventory inventory) {
//        this.inventory = inventory;
//    }
//    public void addSpecialOffer(Product product, double offerPrice) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OFFERS_FILE, true))) {
//            writer.write(product.getId() + "," + product.getName() + "," + offerPrice);
//            writer.newLine();
//            System.out.println("Special offer added successfully!");
//            sendOfferToInventory(product, offerPrice);
//        } catch (IOException e) {
//            System.out.println("Error writing to file: " + e.getMessage());
//        }
//    }
// public void listSpecialOffers() {
//        System.out.println("\nSpecial Offers:");
//        try (BufferedReader reader = new BufferedReader(new FileReader(OFFERS_FILE))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                System.out.println("ID: " + parts[0] + ", Name: " + parts[1] + ", Offer Price: " + parts[2]);
//            }
//        } catch (IOException e) {
//            System.out.println("Error reading file: " + e.getMessage());
//        }
//    }
//    private void sendOfferToInventory(Product product ,double offerPrice) {
//        Product updatedProduct = new Product(product.getId(), product.getName(), product.getPrice(), 
//                                             (int) offerPrice,(int) product.getQuantity() , product.getExpireDate());
//        inventory.updateProduct(product.getId(),updatedProduct);
//        System.out.println("Offer sent to Inventory Management!");
//    }
//    public void generateProductReport(Product product) {
//        System.out.println("\nGenerating Product Report:");
//       product.toString();
//           
//    }
//}
