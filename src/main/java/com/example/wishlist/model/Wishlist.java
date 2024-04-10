package com.example.wishlist.model;

import java.util.List;

public class Wishlist {
    private String wishListName;
    private List<Wish> wishes;

    public Wishlist(String wishListName, List<Wish> wishes){
        this.wishListName = wishListName;
        this.wishes = wishes;
    }
    public Wishlist(){

    }
    public String getWishListName(){
        return wishListName;
    }
    public List<Wish> getWishes(){
        return wishes;
    }
}
