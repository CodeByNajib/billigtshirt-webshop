package dk.ss.backendtshirt.tshirt.service;

import dk.ss.backendtshirt.common.exception.ResourceNotFoundException;
import dk.ss.backendtshirt.tshirt.dto.CartDTO;
import dk.ss.backendtshirt.tshirt.dto.CartItemDTO;
import dk.ss.backendtshirt.tshirt.dto.FreeGiftDTO;
import dk.ss.backendtshirt.tshirt.model.Cart;
import dk.ss.backendtshirt.tshirt.model.GiftProduct;
import dk.ss.backendtshirt.tshirt.model.Product;
import dk.ss.backendtshirt.tshirt.repository.CartRepository;
import dk.ss.backendtshirt.tshirt.repository.GiftProductRepository;
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
    private final GiftProductRepository giftProductRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository,
                      GiftService giftService, GiftProductRepository giftProductRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.giftService = giftService;
        this.giftProductRepository = giftProductRepository;
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



    // ==========================================
    // METHODS FOR TASK 1.1, 1.2, 2.1 & 2.2
    // ==========================================

    /**
     * Metode som Controlleren kalder.
     * 1. Henter data
     * 2. Mapper til DTO
     * 3. Beregner gave-regler (TASK 2.1 & 2.2)
     */
    public CartDTO getCartDetails(Long cartId) {
        Cart cart = getCart(cartId);
        CartDTO cartDTO = mapToDTO(cart);

        // --- TASK 2.1: Hent grænsen DYNAMISK ---
        // Nu: Vi spørger GiftService (som spørger databasen)
        //Calling your existing DB-connected method
        BigDecimal giftThreshold = giftService.getCurrentThreshold();

        // --- TASK 2.2 & 3.1: Logik ---
        BigDecimal currentTotal = cart.getTotalAmount();

        if (currentTotal.compareTo(giftThreshold) >= 0) {
            // Kvalificeret
            cartDTO.setCanSelectFreeGift(true);
            cartDTO.setMissingForFreeGift(BigDecimal.ZERO);
        } else {
            // Ikke kvalificeret
            cartDTO.setCanSelectFreeGift(false);
            BigDecimal missing = giftThreshold.subtract(currentTotal);
            cartDTO.setMissingForFreeGift(missing);
        }

        // Tilføj gratis gave information hvis valgt
        if (cart.getFreeGiftProductId() != null) {
            GiftProduct giftProduct = giftProductRepository.findById(cart.getFreeGiftProductId())
                    .orElse(null);

            if (giftProduct != null) {
                cartDTO.setHasFreeGift(true);
                cartDTO.setFreeGift(new FreeGiftDTO(giftProduct.getName(), giftProduct.getId()));
            } else {
                cartDTO.setHasFreeGift(false);
            }
        } else {
            cartDTO.setHasFreeGift(false);
        }

        return cartDTO;
    }

    /**
     * Hjælpemetode: Konverterer DB Entities til Frontend DTO.
     * Grupperer ens varer (fx 2x T-Shirts).
     */
    public CartDTO mapToDTO(Cart cart) {
        Map<Long, CartItemDTO> itemMap = new HashMap<>();

        // Null-Safety check (som vi lavede i Fase 1)
        List<Product> products = cart.getItems();
        if (products == null) {
            products = new ArrayList<>();
        }

        // Grupperings-logik
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

        List<CartItemDTO> dtoList = new ArrayList<>(itemMap.values());

        // Returner basis objektet (uden gave-logik endnu - det sættes i metoden ovenfor)
        return new CartDTO(dtoList, cart.getTotalAmount());
    }

    /**
     * Vælg gratis gave til kurven (US-K2)
     * Validerer at brugeren opfylder kravene og tilføjer gaven
     */
    public CartDTO selectFreeGift(Long cartId, Long giftProductId) {
        // 1. Hent kurven
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Kurv ikke fundet"));

        // 2. Tjek om beløbsgrænsen er nået
        BigDecimal giftThreshold = giftService.getCurrentThreshold();
        BigDecimal currentTotal = cart.getTotalAmount();

        if (currentTotal.compareTo(giftThreshold) < 0) {
            BigDecimal missing = giftThreshold.subtract(currentTotal);
            throw new IllegalArgumentException("Du har ikke nået beløbsgrænsen for gratis gave. Mangler: " + missing + " kr");
        }

        // 3. Tjek om brugeren allerede har valgt en gave
        if (cart.getFreeGiftProductId() != null) {
            throw new IllegalArgumentException("Du har allerede valgt en gratis gave");
        }

        // 4. Validér at gave produktet eksisterer
        GiftProduct giftProduct = giftProductRepository.findById(giftProductId)
                .orElseThrow(() -> new ResourceNotFoundException("Gave produkt ikke fundet"));

        // 5. Tjek om gaven er på lager
        if (giftProduct.getStockQuantity() == null || giftProduct.getStockQuantity() <= 0) {
            throw new IllegalArgumentException("Dette produkt er ikke på lager");
        }

        // 6. Tilføj gaven til kurven
        cart.setFreeGiftProductId(giftProductId);
        cartRepository.save(cart);

        // 7. Returnér opdateret cart DTO
        return getCartDetails(cartId);
    }

    /**
     * Fjern valgt gratis gave fra kurven
     */
    public CartDTO removeFreeGift(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Kurv ikke fundet"));

        cart.setFreeGiftProductId(null);
        cartRepository.save(cart);

        return getCartDetails(cartId);
    }
}
