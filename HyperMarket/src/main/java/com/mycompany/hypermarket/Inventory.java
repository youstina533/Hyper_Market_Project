//package com.mycompany.hypermarket;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Inventory extends User{

    private final String FILE_NAME = "inventory.txt";
    private List<Product> products = new ArrayList<>();
    
    public Inventory(){
        try {
            loadFromFile(FILE_NAME);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addProduct(Product product) {
        products.add(product);
        try {
            saveToFile(FILE_NAME);
        } catch (IOException ex) {
            System.out.println("Error saving products: " + ex.getMessage());
        }

        System.out.println("Product added successfully!");
        checkNotifications();

    }

    public void deleteProduct(int productID) {
        if (products.removeIf(Product -> Product.getId() == productID)) {
            System.out.println("Product deleted successfully.");
            try {
                saveToFile(FILE_NAME);
            } catch (IOException ex) {
                System.out.println("Error saving products: " + ex.getMessage());
            }
        } else {
            System.out.println("Product not found.");
        }

    }

    public void updateProduct(int productId, Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == productId) {
                products.set(i, updatedProduct);
                System.out.println("Product updated successfully!");
                checkNotifications();

                return;
            }
        }
        try {
                saveToFile(FILE_NAME);
            } catch (IOException ex) {
                System.out.println("Error saving products: " + ex.getMessage());
            }
        System.out.println("Product not found!");
    }

    public List<Product> listProducts() {
        return products;
    }

    public Product searchProduct(int productId) {
        for (Product product : products) {
            if (product.getId() == productId) {
                return product;
            }
        }
        System.out.println("Product not found.");
        return null;
    }

    public void checkNotifications() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Product product : products) {
            if (product.getQuantity() < 5) {
                System.out.println("Low stock alert for product: " + product.getName());
            }

            try {
                LocalDate expiryDate = LocalDate.parse(product.getExpireDate(), formatter);
                long daysToExpire = ChronoUnit.DAYS.between(today, expiryDate);

                if (daysToExpire <= 30) {
                    System.out.println("Expiry alert for product: " + product.getName() + ", expiring in " + daysToExpire + " days.");
                }
            } catch (Exception e) {
                System.out.println("Invalid expiry date format for product: " + product.getName());
            }
        }
    }
    
    public void damagedItems(int productId, int quantity) {
        try {
            for (Product p : products) {
                if (p.getId() == productId) {
                    if (p.getQuantity() >= quantity) {
                        p.setQuantity(p.getQuantity() - quantity);
                        System.out.println("Damaged items processed successfully.");
                    } else {
                        System.out.println("Not enough stock to mark as damaged.");
                    }
                   
                saveToFile(FILE_NAME);
            
                    return;
                }
            }
            System.out.println("Product not found.");
        } catch (IOException ex) {
            System.out.println("Error managing damaged items: " + ex.getMessage());
        }
    }

    public void handleSalesReturn(int productId, int quantity) {
        try {
            for (Product p : products) {
                if (p.getId() == productId) {
                    p.setQuantity(p.getQuantity() + quantity);
                    System.out.println("Sales return processed successfully.");
                    saveToFile(FILE_NAME);
                    return;
                }
            }
            System.out.println("Product not found.");
        } catch (IOException ex) {
            System.out.println("Error handling sales return: " + ex.getMessage());
        }
    }
    
    

    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Product product : products) {
                writer.write(product.getId() + "," + product.getName() + "," + product.getPrice() + "," + product.getQuantity() + "," + product.getExpireDate());
                writer.newLine();
            }
        }
        System.out.println("Products saved to file successfully!");
    }

  public void loadFromFile(String filename) throws IOException {
    products.clear();
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = reader.readLine()) != null) {
            try {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                int offerPrice = Integer.parseInt(parts[3]);
                int quantity = Integer.parseInt(parts[4]);
                String expiryDate = parts[5];
                
                products.add(new Product(id, name, price, offerPrice, quantity, expiryDate));
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Skipping invalid line: " + line);
            }
        }
    }
    System.out.println("Products loaded from file successfully!");
}

}
