package com.example.wishlist.ControllerTest;

import com.example.wishlist.controller.WishController;
import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import com.example.wishlist.service.WishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

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
    void testCreateWish() throws Exception {
        int listId = 1;
        mockMvc.perform(post("/createWish/{id}", listId)
                        .param("name", "Test Wish")
                        .param("itemURL", "google.com")
                        .param("price", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/showWish/" + listId));

        verify(wishService, times(1)).createWish(any(Wish.class), eq(listId));
    }

    @Test
    void testEditWish() throws Exception {
        int wishId = 1;
        int wishlistId = 1;
        when(wishService.findWishlistIdByWishId(eq(wishId))).thenReturn(wishlistId);

        mockMvc.perform(post("/editWish/{id}", wishId)
                        .param("name", "Updated Wish Name")
                        .param("itemURL", "http://updated-example.com")
                        .param("price", "15.00"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/showWish/1"));

        verify(wishService, times(1)).editWish(any(Wish.class), eq(wishId));
    }


    @Test
    void testDeleteWish() throws Exception {
        int wishId = 1;
        int wishlistId = 1;

        when(wishService.findWishlistIdByWishId(eq(wishId))).thenReturn(wishlistId);

        mockMvc.perform(post("/deleteWish/{id}", wishId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/showWish/" + wishlistId));

        verify(wishService, times(1)).findWishlistIdByWishId(eq(wishId));
        verify(wishService, times(1)).deleteWish(eq(wishId));
    }

    @Test
    void testShowWish() throws Exception {
        int wishlistId = 1;
        List<Wish> wishes = Arrays.asList(
                new Wish("Test Wish 1", "http://example.com/1", 10.0, 1),
                new Wish("Test Wish 2", "http://example.com/2", 20.0, 2)
        );
        when(wishService.findAllByWishlistId(eq(wishlistId))).thenReturn(wishes);

        mockMvc.perform(get("/showWish/{id}", wishlistId))
                .andExpect(status().isOk())
                .andExpect(view().name("showWish"))
                .andExpect(model().attribute("listId", wishlistId))
                .andExpect(model().attribute("wishes", wishes));
    }

    @Test
    void testShowWishlist() throws Exception {
        List<Wishlist> wishlists = Arrays.asList(
                new Wishlist("Wishlist 1",1),
                new Wishlist("Wishlist 2",2)
        );
        when(wishService.findAllWishlists()).thenReturn(wishlists);

        mockMvc.perform(get("/showWishlist"))
                .andExpect(status().isOk())
                .andExpect(view().name("showWishlist"))
                .andExpect(model().attribute("wishlists", wishlists));
    }


}
