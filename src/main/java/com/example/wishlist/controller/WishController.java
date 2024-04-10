package com.example.wishlist.controller;

import com.example.wishlist.model.Wishlist;
import com.example.wishlist.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WishController {
    private WishService wishService;
    public WishController(WishService wishService){
        this.wishService = wishService;
    }
    @GetMapping("/home")
    public String getHomePage(){
        return "WishList";
    }
    @GetMapping("/create")
    public String createWishList(Model model) {
        Wishlist defaultWishList = new Wishlist();
        model.addAttribute("wishList", defaultWishList);
        return "redirect:/home";
    }

}
