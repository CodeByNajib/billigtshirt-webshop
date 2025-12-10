package dk.ss.backendtshirt.tshirt.service;

import dk.ss.backendtshirt.common.exception.ResourceNotFoundException;
import dk.ss.backendtshirt.tshirt.model.Cart;
import dk.ss.backendtshirt.tshirt.model.Product;
import dk.ss.backendtshirt.tshirt.repository.CartRepository;
import dk.ss.backendtshirt.tshirt.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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
    public Cart getCart(Long cartId) { // Bemærk: Skal måske ændres til Integer cartId, hvis din Cart bruger int id
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

}
