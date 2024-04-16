package com.example.wishlist.model;

public class Wish {

    private String name;
    private String itemURL;
    private double price;
    private int wishId;


    public Wish(String itemName, String itemURL, double price) {
        this.name = itemName;
        this.itemURL = itemURL;
        this.price = price;
    }

    public Wish() {

    }

    public String getName() {
        return name;
    }

    public String getItemURL() {
        return itemURL;
    }

    public double getPrice() {
        return price;
    }

    public int getWishId() {
        return wishId;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setItemURL(String itemURL) {
        this.itemURL = itemURL;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public void setWishId(int wishId) {
        this.wishId = wishId;
    }
}
