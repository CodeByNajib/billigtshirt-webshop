package dk.ss.backendtshirt.tshirt.service;

import dk.ss.backendtshirt.common.exception.ResourceNotFoundException;
import dk.ss.backendtshirt.tshirt.model.*;
import dk.ss.backendtshirt.tshirt.repository.OrderGiftRepository;
import dk.ss.backendtshirt.tshirt.repository.OrderRepository;
import dk.ss.backendtshirt.tshirt.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderGiftRepository orderGiftRepository;
    private final ProductRepository productRepository;
    private final GiftService giftService;
    private final CartService cartService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OrderGiftRepository orderGiftRepository,
                        ProductRepository productRepository,
                        GiftService giftService,
                        CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderGiftRepository = orderGiftRepository;
        this.productRepository = productRepository;
        this.giftService = giftService;
        this.cartService = cartService;
    }

    public Order createOrderFromCart(Long cartId, Customer customer) {
        // 1. Hent kurven
        Cart cart = cartService.getCart(cartId);

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Kan ikke oprette ordre fra tom kurv");
        }

        // 2. Opret selve ordren
        Order newOrder = new Order();
        newOrder.setCustomer(customer);
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setTotalAmount(cart.getTotalAmount());
        newOrder.setOrderNumber(UUID.randomUUID().toString());
        newOrder.setOriginalGiftThreshold(giftService.getCurrentThreshold()); // Gem grænsen for historik

        newOrder = orderRepository.save(newOrder);

        // 3. Nedskriv lager for ALLE varer i kurven (Acceptkriterie 2)
        for (Product cartItem : cart.getItems()) {
            // Henter frisk produkt fra DB for at sikre lagerstatus er opdateret
            Product dbProduct = productRepository.findById(cartItem.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produkt ikke fundet: " + cartItem.getName()));

            if (dbProduct.getStockQuantity() < 1) {
                throw new IllegalArgumentException("Vare ikke på lager: " + dbProduct.getName());
            }

            dbProduct.setStockQuantity(dbProduct.getStockQuantity() - 1);
            productRepository.save(dbProduct);
        }

        // 4. Håndter gaven (Acceptkriterie 1, 2 & 3)
        checkAndApplyGift(newOrder, cart);

        // 5. Tøm kurven til sidst
        cartService.clearCart(cartId);

        return newOrder;
    }

    private void checkAndApplyGift(Order order, Cart cart) {
        // Acceptkriterie 1: Tjek betingelser
        boolean qualifies = giftService.checkQualification(cart.getTotalAmount());

        if (qualifies) {
            // Her bruger vi ID 1 som eksempel
            Long giftProductId = 1L;

            Product giftProduct = productRepository.findById(giftProductId).orElse(null);

            // Acceptkriterie 2: Reserver lager HVIS tilgængelig
            if (giftProduct != null && giftProduct.getStockQuantity() > 0) {

                // Opret relationen
                OrderGift gift = new OrderGift();
                gift.setOrder(order);
                gift.setAvailable(1);

                // --- RETTELSEN ER HER ---
                // Vi fjerner .intValue(), så vi sender en Long videre
                gift.setGiftProductId(giftProductId);

                orderGiftRepository.save(gift);

                // NEDSKRIV LAGER PÅ GAVEN
                giftProduct.setStockQuantity(giftProduct.getStockQuantity() - 1);
                productRepository.save(giftProduct);

                System.out.println("✅ Gave tilføjet til ordre " + order.getOrderNumber());
            } else {
                // Acceptkriterie 3: Log årsag hvis regler ikke kan opfyldes (pga. lager)
                System.out.println("⚠️ Kunne ikke tilføje gave. Årsag: "
                        + (giftProduct == null ? "Gave-produkt findes ikke" : "Gave ikke på lager"));
            }
        } else {
            // Acceptkriterie 3: Log årsag (pga. beløb)
            System.out.println("ℹ️ Ingen gave. Totalbeløb (" + cart.getTotalAmount() + ") under grænsen.");
        }
    }
}
