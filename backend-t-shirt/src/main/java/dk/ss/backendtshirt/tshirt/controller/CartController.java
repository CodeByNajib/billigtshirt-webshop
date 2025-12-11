package dk.ss.backendtshirt.tshirt.controller;

import dk.ss.backendtshirt.common.exception.ResourceNotFoundException;
import dk.ss.backendtshirt.tshirt.dto.CartDTO;
import dk.ss.backendtshirt.tshirt.model.Cart;
import dk.ss.backendtshirt.tshirt.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
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

    // 3. VÆLG GRATIS GAVE (US-K2) - MED OMFATTENDE DEBUG
    @PostMapping("/select-free-gift")
    public ResponseEntity<?> selectFreeGift(@RequestBody Map<String, Object> request, HttpSession session) {
        try {
            System.out.println("=== SELECT FREE GIFT DEBUG START ===");
            System.out.println("📦 Request body: " + request);

            // Parse gift product ID
            Long giftProductId = null;
            Object giftIdObj = request.get("giftProductId");
            System.out.println("🎁 Gift ID object type: " + (giftIdObj != null ? giftIdObj.getClass().getName() : "null"));
            System.out.println("🎁 Gift ID value: " + giftIdObj);

            if (giftIdObj instanceof Number) {
                giftProductId = ((Number) giftIdObj).longValue();
            } else if (giftIdObj instanceof String) {
                giftProductId = Long.parseLong((String) giftIdObj);
            }

            System.out.println("🎁 Parsed Gift Product ID: " + giftProductId);

            // Hent kurv ID fra session
            Long cartId = (Long) session.getAttribute("cartId");
            System.out.println("🛒 Cart ID from session: " + cartId);

            if (cartId == null) {
                System.err.println("❌ Cart ID is NULL - no cart in session");
                return ResponseEntity.status(404).body(Map.of("message", "Kurv ikke fundet"));
            }

            if (giftProductId == null) {
                System.err.println("❌ Gift Product ID is NULL");
                return ResponseEntity.status(400).body(Map.of("message", "Gift produkt ID mangler"));
            }

            System.out.println("✅ Calling cartService.selectFreeGift(" + cartId + ", " + giftProductId + ")");

            // Vælg gratis gave
            CartDTO updatedCart = cartService.selectFreeGift(cartId, giftProductId);

            System.out.println("✅ Successfully selected free gift!");
            System.out.println("🎉 Updated cart - hasFreeGift: " + updatedCart.isHasFreeGift());
            System.out.println("=== SELECT FREE GIFT DEBUG END ===");

            return ResponseEntity.ok(updatedCart);

        } catch (ResourceNotFoundException e) {
            System.err.println("❌ ResourceNotFoundException: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(404).body(Map.of("message", e.getMessage()));
        } catch (IllegalArgumentException e) {
            System.err.println("❌ IllegalArgumentException: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(400).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            System.err.println("❌❌❌ UNEXPECTED ERROR ❌❌❌");
            System.err.println("Exception type: " + e.getClass().getName());
            System.err.println("Error message: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                "message", "Der skete en fejl: " + e.getMessage(),
                "type", e.getClass().getSimpleName()
            ));
        }
    }

    // 4. FJERN GRATIS GAVE
    @DeleteMapping("/remove-free-gift")
    public ResponseEntity<?> removeFreeGift(HttpSession session) {
        try {
            System.out.println("=== REMOVE FREE GIFT DEBUG START ===");

            Long cartId = (Long) session.getAttribute("cartId");
            System.out.println("🛒 Cart ID from session: " + cartId);

            if (cartId == null) {
                System.err.println("❌ Cart ID is NULL");
                return ResponseEntity.status(404).body(Map.of("message", "Kurv ikke fundet"));
            }

            CartDTO updatedCart = cartService.removeFreeGift(cartId);

            System.out.println("✅ Successfully removed free gift!");
            System.out.println("=== REMOVE FREE GIFT DEBUG END ===");

            return ResponseEntity.ok(updatedCart);

        } catch (ResourceNotFoundException e) {
            System.err.println("❌ ResourceNotFoundException: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(404).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            System.err.println("❌❌❌ UNEXPECTED ERROR ❌❌❌");
            System.err.println("Exception type: " + e.getClass().getName());
            System.err.println("Error message: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                "message", "Der skete en fejl: " + e.getMessage(),
                "type", e.getClass().getSimpleName()
            ));
        }
    }

    // 5. OPDATER VAREANTAL (NY ENDPOINT)
    @PutMapping("/update")
    public ResponseEntity<?> updateCartItem(@RequestBody UpdateCartRequest request, HttpSession session) {
        try {
            System.out.println("=== UPDATE CART ITEM DEBUG START ===");
            System.out.println("📦 Request: productId=" + request.productId + ", quantity=" + request.quantity);

            Long cartId = (Long) session.getAttribute("cartId");
            System.out.println("🛒 Cart ID from session: " + cartId);

            if (cartId == null) {
                System.err.println("❌ Cart ID is NULL");
                return ResponseEntity.status(404).body(Map.of("message", "Kurv ikke fundet"));
            }

            if (request.productId == null) {
                System.err.println("❌ Product ID is NULL");
                return ResponseEntity.status(400).body(Map.of("message", "Product ID mangler"));
            }

            if (request.quantity < 0) {
                System.err.println("❌ Invalid quantity: " + request.quantity);
                return ResponseEntity.status(400).body(Map.of("message", "Antal skal være mindst 0"));
            }

            CartDTO updatedCart;

            if (request.quantity == 0) {
                // Hvis quantity er 0, fjern produktet helt
                System.out.println("🗑️ Quantity is 0, removing product completely");
                updatedCart = cartService.removeCartItem(cartId, request.productId);
            } else {
                // Ellers opdater antallet
                System.out.println("✏️ Updating quantity to " + request.quantity);
                updatedCart = cartService.updateCartItemQuantity(cartId, request.productId, request.quantity);
            }

            System.out.println("✅ Successfully updated cart!");
            System.out.println("=== UPDATE CART ITEM DEBUG END ===");

            return ResponseEntity.ok(updatedCart);

        } catch (ResourceNotFoundException e) {
            System.err.println("❌ ResourceNotFoundException: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(404).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            System.err.println("❌❌❌ UNEXPECTED ERROR ❌❌❌");
            System.err.println("Exception type: " + e.getClass().getName());
            System.err.println("Error message: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                "message", "Der skete en fejl: " + e.getMessage(),
                "type", e.getClass().getSimpleName()
            ));
        }
    }

    // 6. FJERN VARE (NY ENDPOINT)
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeCartItem(@PathVariable Long productId, HttpSession session) {
        try {
            System.out.println("=== REMOVE CART ITEM DEBUG START ===");
            System.out.println("🗑️ Removing productId: " + productId);

            Long cartId = (Long) session.getAttribute("cartId");
            System.out.println("🛒 Cart ID from session: " + cartId);

            if (cartId == null) {
                System.err.println("❌ Cart ID is NULL");
                return ResponseEntity.status(404).body(Map.of("message", "Kurv ikke fundet"));
            }

            CartDTO updatedCart = cartService.removeCartItem(cartId, productId);

            System.out.println("✅ Successfully removed item from cart!");
            System.out.println("=== REMOVE CART ITEM DEBUG END ===");

            return ResponseEntity.ok(updatedCart);

        } catch (ResourceNotFoundException e) {
            System.err.println("❌ ResourceNotFoundException: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(404).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            System.err.println("❌❌❌ UNEXPECTED ERROR ❌❌❌");
            System.err.println("Exception type: " + e.getClass().getName());
            System.err.println("Error message: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                "message", "Der skete en fejl: " + e.getMessage(),
                "type", e.getClass().getSimpleName()
            ));
        }
    }

    // Lille hjælpe-klasse til at læse JSON fra frontend: { "productId": 1, "quantity": 1 }
    public static class AddToCartRequest {
        public Long productId;
        public int quantity;
    }

    // Hjælpe-klasse til UPDATE endpoint: { "productId": 1, "quantity": 3 }
    public static class UpdateCartRequest {
        public Long productId;
        public int quantity;
    }
}
