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


    @PostMapping("/editWish/{id}")
    public String editWish(@ModelAttribute Wish wish, @PathVariable("id") int id) throws SQLException {
        wishService.editWish(wish, id);
        return "redirect:/createWish";
    }


    @PostMapping("/deleteWish/{id}")
    public String deleteWish(@RequestParam("id") int id) throws SQLException {
        wishService.deleteWish(id);
        return "redirect:/createWish";
    }

    @GetMapping("/showWish/{id}")
    public String showWish(@PathVariable("id") int id, Model model) throws SQLException {
        List<Wish> wishes = wishService.findAllByWishlistId(id);

        model.addAttribute("wishes", wishes);

        return "showWish";
    }

    @GetMapping("/showWishlist")
    public String showWishlist(Model model) {
        return "showWishlist";
    }
}


