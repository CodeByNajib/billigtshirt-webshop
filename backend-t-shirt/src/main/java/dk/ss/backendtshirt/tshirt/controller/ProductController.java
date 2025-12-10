package dk.ss.backendtshirt.tshirt.controller;

import dk.ss.backendtshirt.tshirt.dto.ProductRequestDTO;
import dk.ss.backendtshirt.tshirt.dto.ProductResponseDTO;
import dk.ss.backendtshirt.tshirt.model.Product;
import dk.ss.backendtshirt.tshirt.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO requestDTO) {
        Product createdProduct = productService.createProductFromDTO(requestDTO);
        return new ResponseEntity<>(new ProductResponseDTO(createdProduct), HttpStatus.CREATED);
    }

    /**
     * Get ALL products including inactive (Admin)
     * GET /api/products
     * Returns active as INTEGER (1 or 0)
     */
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductResponseDTO> responseDTOs = products.stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Get product by ID (Admin & Customer)
     * GET /api/products/{id}
     * Returns active as INTEGER (1 or 0)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(new ProductResponseDTO(product));
    }

    /**
     * Update product (Admin)
     * PUT /api/products/{id}
     * Accepts active as BOOLEAN (true/false), converts to integer in database
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO requestDTO) {
        Product updatedProduct = productService.updateProductFromDTO(id, requestDTO);
        return ResponseEntity.ok(new ProductResponseDTO(updatedProduct));
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
     * Get only active products (Customer)
     * GET /api/products/active
     * Returns active as INTEGER (1 or 0)
     */
    @GetMapping("/active")
    public ResponseEntity<List<ProductResponseDTO>> getActiveProducts() {
        List<Product> products = productService.getActiveProducts();
        List<ProductResponseDTO> responseDTOs = products.stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
}
