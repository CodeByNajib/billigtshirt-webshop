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

        // --- FIX START: Safety Check ---
        List<Product> products = cart.getItems();
        if (products == null) {
            products = new ArrayList<>(); // Use an empty list instead of null
        }
        // --- FIX END ---

        // 1. Group items logic
        for (Product product : products) {
            Long id = product.getId();

            if (itemMap.containsKey(id)) {
                CartItemDTO existing = itemMap.get(id);
                CartItemDTO updated = new CartItemDTO(
                        existing.getProductName(),
                        existing.getQuantity() + 1,
                        existing.getPricePerUnit()
                );
                itemMap.put(id, updated);
            } else {
                itemMap.put(id, new CartItemDTO(
                        product.getName(),
                        1,
                        product.getPrice()
                ));
            }
        }

        // 2. Convert to List
        List<CartItemDTO> dtoList = new ArrayList<>(itemMap.values());

        // 3. Return DTO
        return new CartDTO(dtoList, cart.getTotalAmount());
    }


}
