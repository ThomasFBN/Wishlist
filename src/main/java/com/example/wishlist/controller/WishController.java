package com.example.wishlist.controller;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import com.example.wishlist.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
public class WishController {
    private WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping("/home")
    public String getHomePage() {
        return "WishList";
    }

    @GetMapping("/create")
    public String createWishList(Model model) {
        Wishlist defaultWishList = new Wishlist();
        model.addAttribute("wishList", defaultWishList);
        return "createWishlist";
    }

    @PostMapping("/create")
    public String wishlistCreation(@ModelAttribute Wishlist wishlist) throws SQLException {
        wishService.createWishlist(wishlist);
        return "redirect:/createWish";
    }

    @GetMapping("/createWish")
    public String createWish(Model model) {
        return "createWish";
    }

    @PostMapping("/editWish/{wishId}")
    public String editWish(@ModelAttribute Wish wish, @PathVariable("wishId") int wishId) throws SQLException {
        wishService.editWish(wish, wishId);
        return "redirect:/createWish";
    }


    @PostMapping("/deleteWish")
    public String deleteWish(@RequestParam("wishId") int wishId) throws SQLException {
        wishService.deleteWish(wishId);
        return "redirect:/createWish";
    }

    @GetMapping("/showWish")
    public String showWish(Model model) {
        return "showWish";
    }


    @GetMapping("/showWishlist")
    public String showWishlist(Model model) {
        return "showWishlist";
    }
}


