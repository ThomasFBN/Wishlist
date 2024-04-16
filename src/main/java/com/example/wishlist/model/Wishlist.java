package com.example.wishlist.model;

import java.util.List;

public class Wishlist {
    private String wishListName;
    private List<Wish> wishes;


    private int listId;

    public Wishlist(String wishListName, List<Wish> wishes) {
        this.wishListName = wishListName;
        this.wishes = wishes;
    }

    public Wishlist() {

    }

    public void setWishListName(String wishListName) {
        this.wishListName = wishListName;
    }

    public String getWishListName() {
        return wishListName;
    }

    public List<Wish> getWishes() {
        return wishes;
    }
    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }
}
