package dk.ss.backendtshirt.tshirt.controller;

import dk.ss.backendtshirt.tshirt.model.GiftProduct;
import dk.ss.backendtshirt.tshirt.service.GiftProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<GiftProduct> createGiftProduct(@Valid @RequestBody GiftProduct giftProduct) {
        GiftProduct createdGiftProduct = giftProductService.createGiftProduct(giftProduct);
        return new ResponseEntity<>(createdGiftProduct, HttpStatus.CREATED);
    }

    /**
     * Get all gift products including inactive (Admin)
     * GET /api/gift-products/admin/all
     */
    @GetMapping("/admin/all")
    public ResponseEntity<List<GiftProduct>> getAllGiftProducts() {
        List<GiftProduct> giftProducts = giftProductService.getAllGiftProducts();
        return ResponseEntity.ok(giftProducts);
    }

    /**
     * Get gift product by ID (Admin & Customer)
     * GET /api/gift-products/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<GiftProduct> getGiftProductById(@PathVariable Long id) {
        return giftProductService.getGiftProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Update gift product (Admin)
     * PUT /api/gift-products/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<GiftProduct> updateGiftProduct(@PathVariable Long id, @Valid @RequestBody GiftProduct giftProductDetails) {
        try {
            GiftProduct updatedGiftProduct = giftProductService.updateGiftProduct(id, giftProductDetails);
            return ResponseEntity.ok(updatedGiftProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deactivate gift product (Admin) - Soft delete
     * PATCH /api/gift-products/{id}/deactivate
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<GiftProduct> deactivateGiftProduct(@PathVariable Long id) {
        try {
            GiftProduct deactivatedGiftProduct = giftProductService.deactivateGiftProduct(id);
            return ResponseEntity.ok(deactivatedGiftProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Activate gift product (Admin)
     * PATCH /api/gift-products/{id}/activate
     */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<GiftProduct> activateGiftProduct(@PathVariable Long id) {
        try {
            GiftProduct activatedGiftProduct = giftProductService.activateGiftProduct(id);
            return ResponseEntity.ok(activatedGiftProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Hard delete gift product (Admin) - Use with caution!
     * DELETE /api/gift-products/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGiftProduct(@PathVariable Long id) {
        try {
            giftProductService.deleteGiftProduct(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Customer endpoints - Only active gift products

    /**
     * Get all active gift products (Customer - for gift selection)
     * GET /api/gift-products
     */
    @GetMapping
    public ResponseEntity<List<GiftProduct>> getActiveGiftProducts() {
        List<GiftProduct> activeGiftProducts = giftProductService.getActiveGiftProducts();
        return ResponseEntity.ok(activeGiftProducts);
    }

    /**
     * Get inactive gift products (Admin)
     * GET /api/gift-products/admin/inactive
     */
    @GetMapping("/admin/inactive")
    public ResponseEntity<List<GiftProduct>> getInactiveGiftProducts() {
        List<GiftProduct> inactiveGiftProducts = giftProductService.getInactiveGiftProducts();
        return ResponseEntity.ok(inactiveGiftProducts);
    }
}
