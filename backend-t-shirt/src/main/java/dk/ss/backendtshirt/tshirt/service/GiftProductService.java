package dk.ss.backendtshirt.tshirt.service;

import dk.ss.backendtshirt.tshirt.model.GiftProduct;
import dk.ss.backendtshirt.tshirt.repository.GiftProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GiftProductService {

    private final GiftProductRepository giftProductRepository;

    @Autowired
    public GiftProductService(GiftProductRepository giftProductRepository) {
        this.giftProductRepository = giftProductRepository;
    }

    // Create a new gift product
    public GiftProduct createGiftProduct(GiftProduct giftProduct) {
        return giftProductRepository.save(giftProduct);
    }

    // Get all gift products (for admin)
    public List<GiftProduct> getAllGiftProducts() {
        return giftProductRepository.findAll();
    }

    // Get gift product by ID
    public Optional<GiftProduct> getGiftProductById(Long id) {
        return giftProductRepository.findById(id);
    }

    // Get only active gift products (for customers)
    public List<GiftProduct> getActiveGiftProducts() {
        return giftProductRepository.findByActiveTrue();
    }

    // Get only available gift products. 'true' for active items. '0' for stock greater than 0
    public List<GiftProduct> getAvailableGiftProducts() {
        return giftProductRepository.findByActiveAndStockQuantityGreaterThan(true, 0);
    }


    // Get inactive gift products
    public List<GiftProduct> getInactiveGiftProducts() {
        return giftProductRepository.findByActiveFalse();
    }

    // Update gift product
    public GiftProduct updateGiftProduct(Long id, GiftProduct giftProductDetails) {
        GiftProduct giftProduct = giftProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gift product not found with id: " + id));

        giftProduct.setName(giftProductDetails.getName());
        giftProduct.setDescription(giftProductDetails.getDescription());
        giftProduct.setImageUrl(giftProductDetails.getImageUrl());
        giftProduct.setStockQuantity(giftProductDetails.getStockQuantity());
        giftProduct.setActive(giftProductDetails.getActive());

        return giftProductRepository.save(giftProduct);
    }

    // Deactivate gift product (soft delete)
    public GiftProduct deactivateGiftProduct(Long id) {
        GiftProduct giftProduct = giftProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gift product not found with id: " + id));

        giftProduct.setActive(false);
        return giftProductRepository.save(giftProduct);
    }

    // Activate gift product
    public GiftProduct activateGiftProduct(Long id) {
        GiftProduct giftProduct = giftProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gift product not found with id: " + id));

        giftProduct.setActive(true);
        return giftProductRepository.save(giftProduct);
    }

    // Hard delete (for testing/admin purposes only)
    public void deleteGiftProduct(Long id) {
        if (!giftProductRepository.existsById(id)) {
            throw new RuntimeException("Gift product not found with id: " + id);
        }
        giftProductRepository.deleteById(id);
    }
}
