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
    void testCreateWish() throws Exception {
        int listId = 1; // Assuming list ID for testing is 1

        mockMvc.perform(MockMvcRequestBuilders.get("/createWish/{id}", listId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("createWish"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listId", "wish"))
                .andExpect(MockMvcResultMatchers.model().attribute("listId", listId))
                .andExpect(MockMvcResultMatchers.model().attribute("wish", new Wish()));

        // Verify that the model attributes are set correctly
        verify(mockModel, times(1)).addAttribute("listId", listId);
        verify(mockModel, times(1)).addAttribute("wish", new Wish());
    }
}


}
