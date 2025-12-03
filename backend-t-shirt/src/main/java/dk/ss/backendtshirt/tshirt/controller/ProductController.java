package dk.ss.backendtshirt.tshirt.controller;

import dk.ss.backendtshirt.tshirt.model.Product;
import dk.ss.backendtshirt.tshirt.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Admin endpoints - CRUD operations

    /**
     * Create a new product (Admin)
     * POST /api/products
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    /**
     * Get all products including inactive (Admin)
     * GET /api/products/admin/all
     */
    @GetMapping("/admin/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Get product by ID (Admin & Customer)
     * GET /api/products/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Update product (Admin)
     * PUT /api/products/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product productDetails) {
        try {
            Product updatedProduct = productService.updateProduct(id, productDetails);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deactivate product (Admin) - Soft delete
     * PATCH /api/products/{id}/deactivate
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Product> deactivateProduct(@PathVariable Long id) {
        try {
            Product deactivatedProduct = productService.deactivateProduct(id);
            return ResponseEntity.ok(deactivatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Activate product (Admin)
     * PATCH /api/products/{id}/activate
     */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<Product> activateProduct(@PathVariable Long id) {
        try {
            Product activatedProduct = productService.activateProduct(id);
            return ResponseEntity.ok(activatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Hard delete product (Admin) - Use with caution!
     * DELETE /api/products/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Customer endpoints - Only active products

    /**
     * Get all active products (Customer)
     * GET /api/products
     */
    @GetMapping
    public ResponseEntity<List<Product>> getActiveProducts() {
        List<Product> activeProducts = productService.getActiveProducts();
        return ResponseEntity.ok(activeProducts);
    }

    /**
     * Get inactive products (Admin)
     * GET /api/products/admin/inactive
     */
    @GetMapping("/admin/inactive")
    public ResponseEntity<List<Product>> getInactiveProducts() {
        List<Product> inactiveProducts = productService.getInactiveProducts();
        return ResponseEntity.ok(inactiveProducts);
    }

    /**
     * Get active products by type (Customer)
     * GET /api/products/type/{productType}
     */
    @GetMapping("/type/{productType}")
    public ResponseEntity<List<Product>> getActiveProductsByType(@PathVariable Product.ProductType productType) {
        List<Product> products = productService.getActiveProductsByType(productType);
        return ResponseEntity.ok(products);
    }

    /**
     * Get active gift products only (Customer - for gift selection)
     * GET /api/products/gifts
     */
    @GetMapping("/gifts")
    public ResponseEntity<List<Product>> getActiveGiftProducts() {
        List<Product> giftProducts = productService.getActiveGiftProducts();
        return ResponseEntity.ok(giftProducts);
    }
}
