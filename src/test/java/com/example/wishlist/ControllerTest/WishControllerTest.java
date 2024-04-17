package com.example.wishlist.ControllerTest;

import com.example.wishlist.controller.WishController;
import com.example.wishlist.model.Wishlist;
import com.example.wishlist.service.WishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(WishController.class)

public class WishControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WishService wishService;

    @Test
    void testGetHomePage() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("WishList"));
    }

    @Test
    void testGetCreateWishListPage() throws Exception {
        mockMvc.perform(get("/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createWishlist"));
    }

    @Test
    void testCreateWishlist() throws Exception {
        when(wishService.getHighestId()).thenReturn(1);

        mockMvc.perform(post("/create")
                        .param("wishListName", "Fødselsdag"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/createWish/1"));

        verify(wishService, times(1)).createWishlist(any(Wishlist.class));
        verify(wishService, times(1)).getHighestId();

    }

    @Test
    void testCreateWish(){

    }


}
