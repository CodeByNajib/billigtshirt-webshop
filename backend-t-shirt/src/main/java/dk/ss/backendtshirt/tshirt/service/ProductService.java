package dk.ss.backendtshirt.tshirt.service;

import dk.ss.backendtshirt.tshirt.model.Product;
import dk.ss.backendtshirt.tshirt.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Create a new product
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Get all products (for admin)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get product by ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Get only active products (for customers)
    public List<Product> getActiveProducts() {
        return productRepository.findByActiveTrue();
    }

    // Get inactive products
    public List<Product> getInactiveProducts() {
        return productRepository.findByActiveFalse();
    }

    // Update product
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setImageUrl(productDetails.getImageUrl());
        product.setSize(productDetails.getSize());
        product.setColor(productDetails.getColor());
        product.setStockQuantity(productDetails.getStockQuantity());
        product.setActive(productDetails.getActive());

        return productRepository.save(product);
    }

    // Deactivate product (soft delete - doesn't affect historical orders)
    public Product deactivateProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setActive(false);
        return productRepository.save(product);
    }

    // Activate product
    public Product activateProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setActive(true);
        return productRepository.save(product);
    }

    // Hard delete (for testing/admin purposes only - be careful with this!)
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
