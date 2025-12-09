//package com.mycompany.hypermarket;
//import java.util.Scanner;
//
//public class HyperMarket {
//    static class Inventory {
//        void Inventory() {
//            System.out.println("[Inventory Module] Managing inventory...");
//        }
//    }
//    static class User {
//        void User() {
//            System.out.println("[User Module] Managing users...");
//        }
//    }
//    static class MarketingModule {
//        void MarketingModule() {
//            System.out.println("[Marketing Module] Managing marketing campaigns...");
//        }
//    }
//
//    static class SellerModule {
//        void SellerModule() {
//            System.out.println("[Sales Module] Managing sales data...");
//        }
//    }
//
//    static class AdminModule{
//        void AdminModule() {
//            System.out.println("[Administrative Module] Managing administrative tasks...");
//        }
//    }
//
//    // Main method: Entry point of the system
//    public static void main(String[] args) {
//        // Instantiate modules
//        Inventory inventory = new Inventory();
//        User user = new User();
//        MarketingModule marketing = new MarketingModule();
//        SellerModule sales = new SellerModule();
//        AdminModule administrative = new AdminModule();
//
//        // Scanner for user input
//        Scanner scanner = new Scanner(System.in);
//        int choice;
//
//        // Menu-based interface
//        do {
//            System.out.println("\n==== System Main Menu ====");
//            System.out.println("1. Manage Inventory");
//            System.out.println("2. Manage Users");
//            System.out.println("3. Manage Marketing");
//            System.out.println("4. Manage Sales");
//            System.out.println("5. Manage Administrative Tasks");
//            System.out.println("6. Exit");
//            System.out.print("Enter your choice: ");
//
//            choice = scanner.nextInt();
//
//            switch (choice) {
//                case 1:
//                    inventory.Inventory();
//                    break;
//                case 2:
//                    user.User();
//                    break;
//                case 3:
//                    marketing.MarketingModule();
//                    break;
//                case 4:
//                    sales.SellerModule();
//                    break;
//                case 5:
////                    administrative.AdminModule();
//                    break;
//                case 6:
//                    System.out.println("Exiting the system. Goodbye!");
//                    break;
//                default:
//                    System.out.println("Invalid choice! Please try again.");
//            }
//        } while (choice != 6);
//
//        // Cleanup
//        scanner.close();
//    }
//}


