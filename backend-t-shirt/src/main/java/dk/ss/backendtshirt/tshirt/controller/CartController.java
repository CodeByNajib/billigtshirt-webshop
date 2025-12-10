package dk.ss.backendtshirt.tshirt.controller;

import dk.ss.backendtshirt.tshirt.dto.CartDTO;
import dk.ss.backendtshirt.tshirt.model.Cart;
import dk.ss.backendtshirt.tshirt.service.CartService;
import org.springframework.web.bind.annotation.*;

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
        // --- MOCK DATA START (Only for testing Task 1.2) ---
        // In real life, you would inject a Repository and findById(1L)
        Cart dummyCart = new Cart();

        // Simulating items from DB
        // You need a Product class constructor or setters here
        // dummyCart.addItem(new Product("T-Shirt", new BigDecimal("200")));
        // dummyCart.addItem(new Product("T-Shirt", new BigDecimal("200")));

        // Since I don't have your Product class code, I can't write the dummy lines perfectly,
        // but ensure your CartService.mapToDTO handles whatever you pass it.

        return cartService.mapToDTO(dummyCart);
        // --- MOCK DATA END ---
    }
}