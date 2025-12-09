
package hypermarket2;
import java.io.*;
import java.util.*;

public class MarketingModule extends User{

    private List<Product> products;
    private static final String PRODUCT_FILE = "employess.txt"; 

    public MarketingModule(List<Product> products) {
        this.products = products;
    }

    // Function to generate reports on product sales or statistics
    public void generateProductReport() {
        System.out.println("\nProduct Sales Report:");
        System.out.printf("%-10s %-20s %-10s %-10s\n", "ID", "Name", "Price", "Quantity");

        for (Product product : products) {
            System.out.printf("%-10d %-20s %-10.2f %-10d\n", product.getId(), product.getName(), product.getPrice(), product.getQuantity());
        }
    }

    // Function to create special offers and apply them to products
    public void createSpecialOffers() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter the Product ID for the offer:");
        int productId = scanner.nextInt();
        System.out.println("Enter the discount percentage:");
        double discountPercentage = scanner.nextDouble();

        Product product = getProductById(productId);
        if (product != null) {
            double discountedPrice = product.getPrice() - (product.getPrice() * (discountPercentage / 100));
            product.setOfferPrice(discountedPrice);
            System.out.println("Special offer applied successfully!");

            // Update the product in the file
            updateProductFile(product);
        } else {
            System.out.println("Product not found!");
        }

        scanner.close();
    }

    // Helper method to get a product by its ID
    private Product getProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    // Method to update the product in the file after applying a special offer
    private void updateProductFile(Product updatedProduct) {
        File inputFile = new File(PRODUCT_FILE);
        File tempFile = new File("temp_products.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0]) == updatedProduct.getId()) {
                    writer.write(updatedProduct.getId() + "," + updatedProduct.getName() + "," + updatedProduct.getPrice() + "," + updatedProduct.getOfferPrice() + "," + updatedProduct.getQuantity() + "," + updatedProduct.getExpireDate());
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