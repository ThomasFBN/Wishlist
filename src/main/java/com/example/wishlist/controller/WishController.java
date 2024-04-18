package com.example.wishlist.controller;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import com.example.wishlist.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
public class WishController {
    private WishService wishService;

    public WishController(WishService wishService) throws SQLException {
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
        int id = wishService.getHighestId();
        return "redirect:/createWish/" + id;
    }

    @GetMapping("/createWish/{id}")
    public String createWish(@PathVariable("id") int id, Model model) {
        model.addAttribute("listId", id);
        model.addAttribute("wish", new Wish());
        return "createWish";
    }

    @PostMapping("/createWish/{id}")
    public String postWish(@PathVariable("id") int id, @ModelAttribute Wish wish, Model model) throws SQLException {
        wish = wishService.createWish(wish, id);
        System.out.println(wish);
        return "redirect:/showWish/" + id;
    }

    @GetMapping("/editWish/{id}")
    public String getEditWishForm(@PathVariable("id") int id, Model model) throws SQLException {
        Wish wish = wishService.findWishById(id);
        model.addAttribute("wish", wish);
        return "editWish";
    }


    @PostMapping("/editWish/{id}")
    public String editWish(@ModelAttribute Wish wish, @PathVariable("id") int id) throws SQLException {
        wishService.editWish(wish, id);
        int wishlistId = wishService.findWishlistIdByWishId(id);
        return "redirect:/showWish/" + wishlistId;
    }


    @PostMapping("/deleteWish/{id}")
    public String deleteWish(@PathVariable("id") int id) throws SQLException {
        int wishlistId = wishService.findWishlistIdByWishId(id);
        wishService.deleteWish(id);
        return "redirect:/showWish/" + wishlistId;
    }


    @GetMapping("/showWish/{id}")
    public String showWish(@PathVariable("id") int id, Model model) throws SQLException {
        List<Wish> wishes = wishService.getWishesFromWishlistId(id);
        model.addAttribute("listId", id);

        model.addAttribute("wishes", wishes);

        return "showWish";
    }

    @GetMapping("/showWishlist")
    public String showWishlist(Model model) throws SQLException {
        List<Wishlist> wishlists = wishService.findAllWishlists();
        model.addAttribute("wishlists", wishlists);
        return "showWishlist";
    }

}


