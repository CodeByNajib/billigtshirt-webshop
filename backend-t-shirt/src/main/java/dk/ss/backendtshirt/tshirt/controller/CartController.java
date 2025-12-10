package dk.ss.backendtshirt.tshirt.controller;

import dk.ss.backendtshirt.tshirt.dto.CartDTO;
import dk.ss.backendtshirt.tshirt.model.Cart;
import dk.ss.backendtshirt.tshirt.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*") // Allows JS to fetch data
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    // For testing: We will just create a DUMMY cart since we aren't fetching by ID yet
    @GetMapping
    public CartDTO getCart() {
        // Phase 1: Simulate User ID 1
        Long fakeUserId = 1L;

        try {
            // Try to find the cart in the DB
            return cartService.getCartDetails(fakeUserId);

        } catch (Exception e) {
            // IF DATABASE FAILS (H2 reset, etc):
            // Print the REAL error to the console so we can see it
            System.err.println("⚠️ CART ERROR: " + e.getMessage());
            e.printStackTrace();

            // FALLBACK: Don't crash (500). Just return a safe, empty cart.
            return new CartDTO(new ArrayList<>(), BigDecimal.ZERO);
        }
    }
}