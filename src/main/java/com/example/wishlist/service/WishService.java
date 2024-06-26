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
        return wishRepository.createWish(wish, listId);
    }

    public void deleteWish(int wishId) throws SQLException {
        wishRepository.deleteWish(wishId);
    }

    public void editWish(Wish wish, int wishId) throws SQLException {
        wishRepository.editWish(wish, wishId);
    }

    public int getHighestId() throws SQLException {
        return wishRepository.getHighestId();
    }

    public List<Wish> getWishesFromWishlistId (int wishListId) throws SQLException {
        return wishRepository.getWishesFromWishlistId(wishListId);
    }

    public int findWishlistIdByWishId(int wishId) throws SQLException {
        return wishRepository.findWishlistIdByWishId(wishId);
    }

    public Wish findWishById(int wishId) throws SQLException {
        return wishRepository.findWishById(wishId);
    }

    public List<Wishlist> findAllWishlists() throws SQLException {
        return wishRepository.findAllWishlists();
    }
}
