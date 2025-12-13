package dk.ss.backendtshirt.tshirt.controller;

import dk.ss.backendtshirt.tshirt.dto.CheckoutRequest;
import dk.ss.backendtshirt.tshirt.dto.GiftRemovedResponse;
import dk.ss.backendtshirt.tshirt.dto.OrderConfirmationDTO;
import dk.ss.backendtshirt.tshirt.model.Cart;
import dk.ss.backendtshirt.tshirt.service.CartService;
import dk.ss.backendtshirt.tshirt.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final CartService cartService;

    @Autowired
    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    /**
     * CHECKOUT ENDPOINT - POST /api/orders/checkout
     *
     * Handles complete checkout process with gift validation:
     * - TASK 1.1: Validates gift rules (threshold)
     * - TASK 2.1: Checks gift stock availability
     * - TASK 2.2: Adds gift to order if available
     * - TASK 3.1: Removes gift and recalculates if needed
     * - TASK 1.3: Comprehensive logging
     *
     * @param request Customer checkout information
     * @param session HttpSession containing the cartId
     * @return OrderConfirmationDTO (success) or GiftRemovedResponse (gift removed)
     */
    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(
            @RequestBody CheckoutRequest request,
            HttpSession session) {

        try {
            log.info("=== CHECKOUT DEBUG START ===");
            log.info("Session ID: {}", session.getId());
            log.info("Customer email: {}", request.getCustomerEmail());
            log.info("All session attributes: {}", Collections.list(session.getAttributeNames()));

            // Hent cartId fra session (ikke cart objekt!)
            Long cartId = (Long) session.getAttribute("cartId");
            log.info("Cart ID from session: {}", cartId);

            if (cartId == null) {
                log.error("❌ Cart ID is NULL in session!");
                return ResponseEntity.badRequest()
                    .body(Map.of(
                        "message", "Kurv er tom - ingen cart ID i session",
                        "sessionId", session.getId()
                    ));
            }

            // Hent cart fra database via cartService
            Cart cart;
            try {
                cart = cartService.getCart(cartId);
            } catch (Exception e) {
                log.error("❌ Failed to fetch cart from database. CartId: {}", cartId, e);
                return ResponseEntity.badRequest()
                    .body(Map.of(
                        "message", "Kunne ikke hente kurv fra database",
                        "cartId", cartId
                    ));
            }

            log.info("✅ Cart found in database!");
            log.info("Cart ID: {}", cart.getId());
            log.info("Cart items: {}", cart.getItems() != null ? cart.getItems().size() : "null");
            log.info("Cart total: {}", cart.getTotalAmount());

            if (cart.getItems() == null || cart.getItems().isEmpty()) {
                log.error("❌ Cart exists but items list is empty!");
                return ResponseEntity.badRequest()
                    .body(Map.of(
                        "message", "Kurv er tom - ingen produkter",
                        "cartId", cart.getId()
                    ));
            }

            log.info("✅ Cart has {} items", cart.getItems().size());

            // Validate customer information
            if (request.getCustomerEmail() == null || request.getCustomerEmail().isEmpty()) {
                log.error("Missing customer email");
                return ResponseEntity.badRequest()
                    .body(Map.of("message", "Kunde email er påkrævet"));
            }

            if (request.getCustomerName() == null || request.getCustomerName().isEmpty()) {
                log.error("Missing customer name");
                return ResponseEntity.badRequest()
                    .body(Map.of("message", "Kunde navn er påkrævet"));
            }

            log.info("=== CHECKOUT DEBUG END ===");

            // Process checkout - returns either OrderConfirmationDTO or GiftRemovedResponse
            Object result = orderService.processCheckout(cart, request);

            // Check what type was returned
            if (result instanceof GiftRemovedResponse) {
                // SCENARIO 3: Gift was removed - need user confirmation
                log.warn("Checkout paused - gift removed. Customer needs to confirm.");
                return ResponseEntity.ok(result);
            } else if (result instanceof OrderConfirmationDTO) {
                // SCENARIO 1 or 2: Order successful (with or without gift)
                OrderConfirmationDTO confirmation = (OrderConfirmationDTO) result;
                log.info("Checkout successful. OrderId: {}, Total: {}, Has Gift: {}",
                         confirmation.getOrderId(),
                         confirmation.getTotalAmount(),
                         confirmation.getFreeGift() != null);

                // Ryd session efter succesfuld ordre
                session.removeAttribute("cartId");
                log.info("Cart ID cleared from session");

                return ResponseEntity.ok(result);
            } else {
                log.error("Unexpected return type from processCheckout");
                return ResponseEntity.status(500)
                    .body(Map.of("message", "Uventet fejl ved checkout"));
            }

        } catch (IllegalArgumentException e) {
            // Business logic errors (empty cart, out of stock, etc.)
            log.error("Checkout validation error: {}", e.getMessage());
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));

        } catch (Exception e) {
            // Unexpected system errors
            log.error("Checkout system error: ", e);
            return ResponseEntity.status(500)
                .body(Map.of("message", "Checkout fejlede: " + e.getMessage()));
        }
    }

    /**
     * GET endpoint to retrieve order by ID
     * Can be used for order confirmation page
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable Long orderId) {
        try {
            log.info("Order retrieval requested for orderId: {}", orderId);
            return ResponseEntity.ok(Map.of("message", "Order endpoint - to be implemented"));
        } catch (Exception e) {
            log.error("Error retrieving order: ", e);
            return ResponseEntity.status(500)
                .body(Map.of("message", "Fejl ved hentning af ordre"));
        }
    }
}

