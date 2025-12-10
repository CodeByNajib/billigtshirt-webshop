package dk.ss.backendtshirt.tshirt.service;

import dk.ss.backendtshirt.common.exception.ResourceNotFoundException;
import dk.ss.backendtshirt.tshirt.dto.GiftProductRequestDTO;
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

    // Create gift product from DTO with validation
    public GiftProduct createGiftProductFromDTO(GiftProductRequestDTO dto) {
        validateGiftProductRequest(dto);

        GiftProduct giftProduct = new GiftProduct();
        giftProduct.setName(dto.getName());
        giftProduct.setDescription(dto.getDescription());
        giftProduct.setImageUrl(dto.getImageUrl());
        giftProduct.setStockQuantity(dto.getStockQuantity());

        // Convert boolean to Boolean (true/false -> true/false in DB, displayed as 1/0 in DTO)
        giftProduct.setActive(dto.getActive() != null ? dto.getActive() : true);

        return giftProductRepository.save(giftProduct);
    }

    // Update gift product from DTO with validation
    public GiftProduct updateGiftProductFromDTO(Long id, GiftProductRequestDTO dto) {
        validateGiftProductRequest(dto);

        return giftProductRepository.findById(id)
                .map(giftProduct -> {
                    giftProduct.setName(dto.getName());
                    giftProduct.setDescription(dto.getDescription());
                    giftProduct.setImageUrl(dto.getImageUrl());
                    giftProduct.setStockQuantity(dto.getStockQuantity());

                    // Convert boolean to Boolean
                    giftProduct.setActive(dto.getActive() != null ? dto.getActive() : true);

                    return giftProductRepository.save(giftProduct);
                })
                .orElseThrow(() ->
                        new ResourceNotFoundException("Gave produkt med id: " + id + " ikke fundet."));
    }

    // Get gift product by ID or throw exception
    public GiftProduct getGiftProductByIdOrThrow(Long id) {
        return giftProductRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Gave produkt med id: " + id + " ikke fundet."));
    }

    // Validate gift product request
    private void validateGiftProductRequest(GiftProductRequestDTO dto) {
        // Validate name
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Gave produkt navn er påkrævet");
        }

        // Validate stock quantity
        if (dto.getStockQuantity() == null || dto.getStockQuantity() < 0) {
            throw new IllegalArgumentException("Lager antal skal være 0 eller større");
        }
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
            throw new ResourceNotFoundException("Gave produkt med id: " + id + " ikke fundet.");
        }
        giftProductRepository.deleteById(id);
    }
}
