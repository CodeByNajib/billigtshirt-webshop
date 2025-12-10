package dk.ss.backendtshirt.tshirt.controller;

import dk.ss.backendtshirt.tshirt.dto.CartDTO;
import dk.ss.backendtshirt.tshirt.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*") // Allows JS to fetch data
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    // 1. HENT KURV
    @GetMapping
    public CartDTO getCart() {
        Long fakeUserId = 1L;

        try {
            return cartService.getCartDetails(fakeUserId);
        } catch (Exception e) {
            System.out.println("♻️ Kurv mangler - opretter ny til bruger 1...");
            cartService.createNewCart();
            return cartService.getCartDetails(fakeUserId);
        }
    }

    // 2. TILFØJ VARE
    @PostMapping("/{productId}")
    public CartDTO addProductToCart(@PathVariable Long productId) {
        Long fakeUserId = 1L;

        try {
            System.out.println("➕ Tilføjer produkt " + productId + " til kurv...");
            cartService.addProductToCart(fakeUserId, productId);
            return cartService.getCartDetails(fakeUserId);
        } catch (Exception e) {
            System.err.println("🔥 Fejl ved tilføjelse: " + e.getMessage());

            // --- HER VAR FEJLEN ---
            // Vi laver en tom nød-DTO med kun 2 argumenter
            CartDTO errorCart = new CartDTO(new ArrayList<>(), BigDecimal.ZERO);
            // Sæt standardværdier for gave-felterne via settere
            errorCart.setCanSelectFreeGift(false);
            errorCart.setMissingForFreeGift(BigDecimal.ZERO);

            return errorCart;
        }
    }
}