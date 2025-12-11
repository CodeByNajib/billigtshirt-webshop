package dk.ss.backendtshirt.tshirt.controller;

import dk.ss.backendtshirt.tshirt.dto.CartDTO;
import dk.ss.backendtshirt.tshirt.model.Cart;
import dk.ss.backendtshirt.tshirt.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 1. HENT KURV (Nu med Session!)
    @GetMapping
    public ResponseEntity<CartDTO> getCart(HttpSession session) {
        // Hent det gemte ID fra brugerens "armbånd" (session)
        Long cartId = (Long) session.getAttribute("cartId");

        if (cartId == null) {
            // Hvis brugeren er ny, viser vi bare en tom kurv (uden at oprette en i DB endnu)
            // Dette sparer plads i databasen indtil de faktisk køber noget.
            CartDTO emptyCart = new CartDTO(new ArrayList<>(), BigDecimal.ZERO);
            emptyCart.setCanSelectFreeGift(false);
            emptyCart.setMissingForFreeGift(BigDecimal.ZERO); // Eller 499.0
            return ResponseEntity.ok(emptyCart);
        }

        // Hvis vi kender ID'et, henter vi den rigtige kurv
        return ResponseEntity.ok(cartService.getCartDetails(cartId));
    }

    // 2. TILFØJ VARE (Rettet til at matche Frontend JSON)
    @PostMapping
    public ResponseEntity<CartDTO> addProductToCart(@RequestBody AddToCartRequest request, HttpSession session) {
        try {
            // 1. Tjek om vi allerede har en kurv
            Long cartId = (Long) session.getAttribute("cartId");

            // 2. Hvis ikke, opret en ny kurv og GEM ID'et i sessionen!
            if (cartId == null) {
                Cart newCart = cartService.createNewCart();
                cartId = newCart.getId();
                session.setAttribute("cartId", cartId); // <--- HER ER MAGIEN! Vi gemmer ID'et.
            }

            System.out.println("➕ Tilføjer produkt " + request.productId + " til kurv ID: " + cartId);

            // 3. Tilføj varen
            cartService.addProductToCart(cartId, request.productId);

            // 4. Returner den opdaterede kurv
            return ResponseEntity.ok(cartService.getCartDetails(cartId));

        } catch (Exception e) {
            System.err.println("🔥 Fejl ved tilføjelse: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Lille hjælpe-klasse til at læse JSON fra frontend: { "productId": 1, "quantity": 1 }
    public static class AddToCartRequest {
        public Long productId;
        public int quantity;
    }
}