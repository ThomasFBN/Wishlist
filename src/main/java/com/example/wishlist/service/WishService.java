package com.example.wishlist.service;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import com.example.wishlist.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class WishService {
    private WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }
    public void createWishlist(Wishlist wishlist) throws SQLException {
        wishRepository.createWishlist(wishlist);
    }
    public Wish createWish(Wish wish, int listId) throws SQLException {
        return wishRepository.createWish(wish,listId);
    }

    public void deleteWish(int wishId) throws SQLException{
        wishRepository.deleteWish(wishId);
    }
    public void editWish(Wish wish, int wishId)throws SQLException{
        wishRepository.editWish(wish, wishId);
    }

}
