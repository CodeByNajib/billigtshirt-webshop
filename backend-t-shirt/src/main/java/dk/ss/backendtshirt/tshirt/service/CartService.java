package dk.ss.backendtshirt.tshirt.service;

import dk.ss.backendtshirt.common.exception.ResourceNotFoundException;
import dk.ss.backendtshirt.tshirt.dto.CartDTO;
import dk.ss.backendtshirt.tshirt.dto.CartItemDTO;
import dk.ss.backendtshirt.tshirt.model.Cart;
import dk.ss.backendtshirt.tshirt.model.Product;
import dk.ss.backendtshirt.tshirt.repository.CartRepository;
import dk.ss.backendtshirt.tshirt.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final GiftService giftService;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository, GiftService giftService) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.giftService = giftService;
    }

    // Hent eller opret kurv
    public Cart getCart(Long cartId) {

        if (cartId == null || cartId == 0) {
            return createNewCart();
        }
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Kurv ikke fundet"));
    }

    public Cart createNewCart() {
        return cartRepository.save(new Cart());
    }

    // Tilføj vare
    public Cart addProductToCart(Long cartId, Long productId) {
        Cart cart = getCart(cartId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Produkt ikke fundet"));

        cart.addItem(product); // Opdaterer også totalen automatisk
        return cartRepository.save(cart);
    }

    // Fjern vare
    public Cart removeProductFromCart(Long cartId, Long productId) {
        Cart cart = getCart(cartId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Produkt ikke fundet"));

        cart.removeItem(product);
        return cartRepository.save(cart);
    }

    // Tøm kurv
    public void clearCart(Long cartId) {
        Cart cart = getCart(cartId);
        cart.getItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    // --- USER STORY VALIDERING (Acceptkriterie 1 & 3) ---

    /**
     * Tjekker om en specifik kurv kvalificerer til gaven LIGE NU.
     * Kan kaldes fra Controlleren for at vise status til brugeren.
     */
    public boolean isEligibleForGift(Long cartId) {
        Cart cart = getCart(cartId);
        return giftService.checkQualification(cart.getTotalAmount());
    }



    // METHODS FOR TASK 1.1 & 1.2 (DTO Logic)

    /**
     * This is the method the Controller should call.
     * It fetches the Entity -> Converts to DTO -> Returns JSON-ready object.
     */
    public CartDTO getCartDetails(Long cartId) {
        Cart cart = getCart(cartId);
        return mapToDTO(cart);
    }

    /**
     * Helper method: Converts raw DB data into frontend-friendly data.
     * Groups identical items (e.g. 2x T-Shirts).
     */
    public CartDTO mapToDTO(Cart cart) {
        Map<Long, CartItemDTO> itemMap = new HashMap<>();

        // 1. Group items logic
        for (Product product : cart.getItems()) {
            Long id = product.getId();

            if (itemMap.containsKey(id)) {
                // If item exists, increase quantity
                CartItemDTO existing = itemMap.get(id);
                CartItemDTO updated = new CartItemDTO(
                        existing.getProductName(),
                        existing.getQuantity() + 1,
                        existing.getPricePerUnit()
                );
                itemMap.put(id, updated);
            } else {
                // New item
                itemMap.put(id, new CartItemDTO(
                        product.getName(),
                        1,
                        product.getPrice()
                ));
            }
        }

        // 2. Create the list for DTO
        List<CartItemDTO> dtoList = new ArrayList<>(itemMap.values());

        // 3. Create the DTO
        CartDTO cartDTO = new CartDTO(dtoList, cart.getTotalAmount());

        // --- PREPARE FOR TASK 2 & 3 (Gift Logic) ---
        // Since you already have giftService injected, we can use it now or later.
        // For Phase 1, we can leave these purely as placeholders or basic logic:

        // This calculates if they qualify (Task 3)
        boolean qualifies = giftService.checkQualification(cart.getTotalAmount());
        cartDTO.setCanSelectFreeGift(qualifies);

        // Task 2: Calculate missing amount (Assuming GiftService has a getLimit() method)
        // If GiftService doesn't have getLimit() yet, just skip this line for now.
        // BigDecimal limit = new BigDecimal("400.00");
        // if (!qualifies) {
        //    cartDTO.setMissingForFreeGift(limit.subtract(cart.getTotalAmount()));
        // }

        return cartDTO;
    }


}
