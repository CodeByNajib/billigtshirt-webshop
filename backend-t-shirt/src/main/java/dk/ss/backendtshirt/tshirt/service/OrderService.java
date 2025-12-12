package dk.ss.backendtshirt.tshirt.service;

import dk.ss.backendtshirt.common.exception.ResourceNotFoundException;
import dk.ss.backendtshirt.tshirt.dto.*;
import dk.ss.backendtshirt.tshirt.model.*;
import dk.ss.backendtshirt.tshirt.repository.CartRepository;
import dk.ss.backendtshirt.tshirt.repository.GiftProductRepository;
import dk.ss.backendtshirt.tshirt.repository.OrderGiftRepository;
import dk.ss.backendtshirt.tshirt.repository.OrderRepository;
import dk.ss.backendtshirt.tshirt.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final OrderGiftRepository orderGiftRepository;
    private final ProductRepository productRepository;
    private final GiftService giftService;
    private final CartService cartService;
    private final GiftProductRepository giftProductRepository;
    private final CartRepository cartRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OrderGiftRepository orderGiftRepository,
                        ProductRepository productRepository,
                        GiftService giftService,
                        CartService cartService,
                        GiftProductRepository giftProductRepository,
                        CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.orderGiftRepository = orderGiftRepository;
        this.productRepository = productRepository;
        this.giftService = giftService;
        this.cartService = cartService;
        this.giftProductRepository = giftProductRepository;
        this.cartRepository = cartRepository;
    }

    /**
     * TASK 1.1, 2.1, 2.2, 3.1 - Complete checkout with gift handling
     * Returns either OrderConfirmationDTO or GiftRemovedResponse
     */
    public Object processCheckout(Cart cart, CheckoutRequest request) {
        log.info("=== CHECKOUT START ===");
        log.info("Customer: {}", request.getCustomerEmail());

        // Cart is already validated in controller, but double-check
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            log.error("Checkout failed: Cart is empty");
            throw new IllegalArgumentException("Kurv er tom");
        }

        log.info("Cart Total: {}", cart.getTotalAmount());
        log.info("Has Free Gift: {}", cart.getFreeGiftProductId() != null);

        // 2. TASK 1.1 & 2.1: Validate gift rules if gift is selected
        if (cart.getFreeGiftProductId() != null) {
            GiftRemovedResponse giftValidation = validateGiftAtCheckout(cart);
            if (giftValidation != null) {
                log.warn("Checkout paused - gift removed. Reason: {}", giftValidation.getMessage());
                return giftValidation;
            }
        }

        // 3. Create order with customer information
        Order order = createOrderWithCustomerInfo(cart, request);

        // 4. TASK 2.2: Add gift to order if available
        if (cart.getFreeGiftProductId() != null) {
            addGiftToOrder(order, cart.getFreeGiftProductId());
        }

        // 5. Reduce product stock
        reduceProductStock(cart);

        log.info("Order created successfully. OrderId: {}, Total: {}", order.getId(), order.getTotalAmount());
        log.info("=== CHECKOUT END ===");

        // 6. Return confirmation
        return toOrderConfirmationDTO(order, request, cart);
    }

    /**
     * TASK 1.1 & 2.1: Validate gift at checkout
     * Returns GiftRemovedResponse if gift needs to be removed, null if validation passes
     */
    private GiftRemovedResponse validateGiftAtCheckout(Cart cart) {
        GiftProduct giftProduct = giftProductRepository.findById(cart.getFreeGiftProductId()).orElse(null);

        if (giftProduct == null) {
            log.warn("Gift validation failed: Gift product not found");
            cart.setFreeGiftProductId(null);
            cartRepository.save(cart);
            return createGiftRemovedResponse(
                "Ukendt gave",
                "Den valgte gave findes ikke længere i systemet.",
                cartService.mapToDTO(cart)
            );
        }

        BigDecimal threshold = giftService.getCurrentThreshold();
        BigDecimal currentTotal = cart.getTotalAmount();

        // TASK 1.1: Check threshold
        if (currentTotal.compareTo(threshold) < 0) {
            log.warn("GIFT REMOVED - Threshold not met. Total: {}, Threshold: {}", currentTotal, threshold);
            cart.setFreeGiftProductId(null);
            cartRepository.save(cart);
            return createGiftRemovedResponse(
                giftProduct.getName(),
                "Din ordre total er faldet under beløbsgrænsen (" + threshold + " kr). Gaven er fjernet.",
                cartService.mapToDTO(cart)
            );
        }

        // TASK 2.1: Check stock
        if (giftProduct.getStockQuantity() == null || giftProduct.getStockQuantity() <= 0) {
            log.warn("GIFT OUT OF STOCK - Removing from cart. Gift: {}, CartId: {}",
                     giftProduct.getName(), cart.getId());
            cart.setFreeGiftProductId(null);
            cartRepository.save(cart);
            return createGiftRemovedResponse(
                giftProduct.getName(),
                "Din valgte gave er desværre udsolgt. Klik for at fortsætte uden gave.",
                cartService.mapToDTO(cart)
            );
        }

        log.info("Gift validation passed. Gift: {}, Stock: {}",
                 giftProduct.getName(), giftProduct.getStockQuantity());
        return null;
    }

    /**
     * TASK 2.2: Add gift to order and reduce stock
     */
    private void addGiftToOrder(Order order, Long giftProductId) {
        GiftProduct giftProduct = giftProductRepository.findById(giftProductId)
            .orElseThrow(() -> new ResourceNotFoundException("Gave produkt ikke fundet"));

        // Create order gift relation
        OrderGift orderGift = new OrderGift();
        orderGift.setOrder(order);
        orderGift.setGiftProductId(giftProductId);
        orderGift.setAvailable(1);
        orderGiftRepository.save(orderGift);

        // Reduce gift stock
        giftProduct.setStockQuantity(giftProduct.getStockQuantity() - 1);
        giftProductRepository.save(giftProduct);

        log.info("Free gift added to order. OrderId: {}, Gift: {}, Remaining Stock: {}",
                 order.getId(), giftProduct.getName(), giftProduct.getStockQuantity());
    }

    /**
     * Create order entity from cart and request with customer information
     */
    private Order createOrderWithCustomerInfo(Cart cart, CheckoutRequest request) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setTotalAmount(cart.getTotalAmount());
        order.setOrderDate(LocalDateTime.now());
        order.setOriginalGiftThreshold(giftService.getCurrentThreshold());

        // Set customer information directly on order
        order.setCustomerName(request.getCustomerName());
        order.setCustomerEmail(request.getCustomerEmail());
        order.setCustomerPhone(request.getCustomerPhone());
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setNotes(request.getNotes());

        log.info("Creating order for customer: {}, Total: {}", request.getCustomerName(), cart.getTotalAmount());

        return orderRepository.save(order);
    }

    /**
     * Reduce stock for all products in cart
     */
    private void reduceProductStock(Cart cart) {
        for (Product cartItem : cart.getItems()) {
            Product dbProduct = productRepository.findById(cartItem.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Produkt ikke fundet: " + cartItem.getId()));

            if (dbProduct.getStockQuantity() < 1) {
                throw new IllegalArgumentException("Vare ikke på lager: " + dbProduct.getName());
            }

            dbProduct.setStockQuantity(dbProduct.getStockQuantity() - 1);
            productRepository.save(dbProduct);
        }
    }

    /**
     * Helper: Create GiftRemovedResponse
     */
    private GiftRemovedResponse createGiftRemovedResponse(String giftName, String message, CartDTO updatedCart) {
        return new GiftRemovedResponse(true, giftName, message, updatedCart);
    }

    /**
     * Helper: Map Order to OrderConfirmationDTO
     */
    private OrderConfirmationDTO toOrderConfirmationDTO(Order order, CheckoutRequest request, Cart cart) {
        // Map cart items to OrderItemDTO with quantities
        Map<Long, OrderItemDTO> itemMap = new HashMap<>();

        for (Product product : cart.getItems()) {
            Long id = product.getId();

            if (itemMap.containsKey(id)) {
                OrderItemDTO existing = itemMap.get(id);
                existing.setQuantity(existing.getQuantity() + 1);
                existing.setRowTotal(existing.getPricePerUnit().multiply(new BigDecimal(existing.getQuantity())));
            } else {
                OrderItemDTO dto = new OrderItemDTO();
                dto.setProductName(product.getName());
                dto.setQuantity(1);
                dto.setPricePerUnit(product.getPrice());
                dto.setRowTotal(product.getPrice());
                itemMap.put(id, dto);
            }
        }

        List<OrderItemDTO> items = new ArrayList<>(itemMap.values());

        // Add free gift if present
        FreeGiftDTO freeGiftDTO = null;
        if (cart.getFreeGiftProductId() != null) {
            GiftProduct giftProduct = giftProductRepository.findById(cart.getFreeGiftProductId()).orElse(null);
            if (giftProduct != null) {
                freeGiftDTO = new FreeGiftDTO(giftProduct.getName(), giftProduct.getId());
                log.info("Including free gift in confirmation: {}", giftProduct.getName());
            }
        }

        return new OrderConfirmationDTO(
            order.getId(),
            request.getCustomerName(),
            request.getCustomerEmail(),
            request.getCustomerPhone(),
            request.getDeliveryAddress(),
            items,
            freeGiftDTO,
            order.getTotalAmount(),
            order.getOrderDate()
        );
    }
}
