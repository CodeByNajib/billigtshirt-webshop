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
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
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
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Get product by ID (Admin & Customer)
     * GET /api/products/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        // Bemærk: Ingen .orElse() eller .map() nødvendig mere.
        // Hvis ID ikke findes, kaster Service en fejl, som GlobalExceptionHandler griber.
        return ResponseEntity.ok(productService.getProductById(id));
    }

    /**
     * Update product (Admin)
     * PUT /api/products/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product productDetails) {
        // Bemærk: Ingen try-catch. Fejlhåndtering sker automatisk.
        return ResponseEntity.ok(productService.updateProduct(id, productDetails));
    }

    /**
     * Deactivate product (Admin) - Soft delete
     * PATCH /api/products/{id}/deactivate
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Product> deactivateProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deactivateProduct(id));
    }

    /**
     * Activate product (Admin)
     * PATCH /api/products/{id}/activate
     */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<Product> activateProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.activateProduct(id));
    }

    /**
     * Hard delete product (Admin) - Use with caution!
     * DELETE /api/products/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get inactive products (Admin)
     * GET /api/products/admin/inactive
     */
    @GetMapping("/admin/inactive")
    public ResponseEntity<List<Product>> getInactiveProducts() {
        return ResponseEntity.ok(productService.getInactiveProducts());
    }

    // --- CUSTOMER ENDPOINTS ---

    /**
     * Get all active products (Customer)
     * GET /api/products
     */
    @GetMapping
    public ResponseEntity<List<Product>> getActiveProducts() {
        return ResponseEntity.ok(productService.getActiveProducts());
    }
}
