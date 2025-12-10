package dk.ss.backendtshirt.tshirt.controller;

import dk.ss.backendtshirt.tshirt.dto.GiftProductRequestDTO;
import dk.ss.backendtshirt.tshirt.dto.GiftProductResponseDTO;
import dk.ss.backendtshirt.tshirt.model.GiftProduct;
import dk.ss.backendtshirt.tshirt.service.GiftProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gift-products")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class GiftProductController {

    private final GiftProductService giftProductService;

    @Autowired
    public GiftProductController(GiftProductService giftProductService) {
        this.giftProductService = giftProductService;
    }

    // Admin endpoints - CRUD operations

    /**
     * Create a new gift product (Admin)
     * POST /api/gift-products
     */
    @PostMapping
    public ResponseEntity<GiftProductResponseDTO> createGiftProduct(@Valid @RequestBody GiftProductRequestDTO requestDTO) {
        GiftProduct createdGiftProduct = giftProductService.createGiftProductFromDTO(requestDTO);
        return new ResponseEntity<>(new GiftProductResponseDTO(createdGiftProduct), HttpStatus.CREATED);
    }

    /**
     * Get ALL gift products including inactive (Admin)
     * GET /api/gift-products
     * Returns active as INTEGER (1 or 0)
     */
    @GetMapping
    public ResponseEntity<List<GiftProductResponseDTO>> getAllGiftProducts() {
        List<GiftProduct> giftProducts = giftProductService.getAllGiftProducts();
        List<GiftProductResponseDTO> responseDTOs = giftProducts.stream()
                .map(GiftProductResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Get gift product by ID (Admin & Customer)
     * GET /api/gift-products/{id}
     * Returns active as INTEGER (1 or 0)
     */
    @GetMapping("/{id}")
    public ResponseEntity<GiftProductResponseDTO> getGiftProductById(@PathVariable Long id) {
        GiftProduct giftProduct = giftProductService.getGiftProductByIdOrThrow(id);
        return ResponseEntity.ok(new GiftProductResponseDTO(giftProduct));
    }

    /**
     * Update gift product (Admin)
     * PUT /api/gift-products/{id}
     * Accepts active as BOOLEAN (true/false), converts to integer in database
     */
    @PutMapping("/{id}")
    public ResponseEntity<GiftProductResponseDTO> updateGiftProduct(
            @PathVariable Long id,
            @Valid @RequestBody GiftProductRequestDTO requestDTO) {
        GiftProduct updatedGiftProduct = giftProductService.updateGiftProductFromDTO(id, requestDTO);
        return ResponseEntity.ok(new GiftProductResponseDTO(updatedGiftProduct));
    }

    /**
     * Hard delete gift product (Admin) - Use with caution!
     * DELETE /api/gift-products/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGiftProduct(@PathVariable Long id) {
        giftProductService.deleteGiftProduct(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get only active gift products (Customer)
     * GET /api/gift-products/active
     * Returns active as INTEGER (1 or 0)
     */
    @GetMapping("/active")
    public ResponseEntity<List<GiftProductResponseDTO>> getActiveGiftProducts() {
        List<GiftProduct> giftProducts = giftProductService.getActiveGiftProducts();
        List<GiftProductResponseDTO> responseDTOs = giftProducts.stream()
                .map(GiftProductResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
}
