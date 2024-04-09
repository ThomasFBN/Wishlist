package com.example.wishlist.model;

public class Wish {

    private String name;
    private String description;
    private String itemURL;
    private double price;

    public Wish(String itemName, String description, String itemURL, double price) {
        this.name = itemName;
        this.itemURL = itemURL;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public String getItemURL(){
        return itemURL;
    }
    public double getPrice(){
        return price;
    }

}
