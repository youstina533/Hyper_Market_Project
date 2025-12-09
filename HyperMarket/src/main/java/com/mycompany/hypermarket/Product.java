//package com.mycompany.hypermarket;
import java.util.*;
import java.util.concurrent.TimeUnit;
public class Product extends User {
    private int id;
    private String name;
    private double price;
    private double offerPrice = -1;
    private int quantity;
    private Date addedAt;
    private String expireDate;
    private String type;
    
    public Product(int id, String name, double price, int quantity, String type){
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        addedAt = new Date();
    }
    
    public Product(int id, String name, double price, int quantity, String type, String expireDate){
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.expireDate = expireDate;
        addedAt = new Date();
    }
    
    public Product(String name, double price, String expireDate, String type){
        this.name = name;
        this.price = price;
        this.expireDate = expireDate;
        this.type = type;
        addedAt = new Date();
    }
    
    public Product(int id, String name, double price, double offerPrice, 
      int quantity, String expireDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.offerPrice = offerPrice;
        this.quantity = quantity;
        addedAt = new Date();
        this.expireDate = expireDate;
    }
    
    public int getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    
     public double getOfferPrice() {
        return this.offerPrice;
    }
    public void setOfferPrice(double offerPrice) {
        this.offerPrice = offerPrice;
    }
    
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public Date getAddedAtDate() {
        return addedAt;
    }
    
    public String getExpireDate() {
       return expireDate;
   }
     
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    } 
    
    public Date getAddedDate(){
        return addedAt;
    }
     @Override
    public String toString() {
        return "Product ID: " + id + ", Name: " + name + ", Price: " + price + ", Quantity: " + quantity + ", Expiry Date: " + expireDate;
    }
}

