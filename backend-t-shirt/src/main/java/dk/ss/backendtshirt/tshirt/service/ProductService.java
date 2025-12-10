package dk.ss.backendtshirt.tshirt.service;

import dk.ss.backendtshirt.common.exception.ResourceNotFoundException;
import dk.ss.backendtshirt.tshirt.dto.ProductRequestDTO;
import dk.ss.backendtshirt.tshirt.model.Color;
import dk.ss.backendtshirt.tshirt.model.Product;
import dk.ss.backendtshirt.tshirt.model.Size;
import dk.ss.backendtshirt.tshirt.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Create product from DTO with validation
    public Product createProductFromDTO(ProductRequestDTO dto) {
        validateProductRequest(dto);

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImageUrl());
        product.setStockQuantity(dto.getStockQuantity());

        // Convert string to enum with validation
        product.setSize(dto.getSize());
        product.setColor(dto.getColor());

        // Convert boolean to Boolean (true/false -> true/false in DB, displayed as 1/0 in DTO)
        product.setActive(dto.getActive() != null ? dto.getActive() : true);

        return productRepository.save(product);
    }

    // Update product from DTO with validation
    public Product updateProductFromDTO(Long id, ProductRequestDTO dto) {
        validateProductRequest(dto);

        return productRepository.findById(id)
                .map(product -> {
                    product.setName(dto.getName());
                    product.setDescription(dto.getDescription());
                    product.setPrice(dto.getPrice());
                    product.setImageUrl(dto.getImageUrl());
                    product.setStockQuantity(dto.getStockQuantity());

                    // Convert string to enum with validation
                    product.setSize(dto.getSize());
                    product.setColor(dto.getColor());

                    // Convert boolean to Boolean
                    product.setActive(dto.getActive() != null ? dto.getActive() : true);

                    return productRepository.save(product);
                })
                .orElseThrow(() ->
                        new ResourceNotFoundException("Produkt med id: " + id + " ikke fundet."));
    }

    // Validate product request
    private void validateProductRequest(ProductRequestDTO dto) {
        // Validate price
        if (dto.getPrice() == null || dto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Pris skal være større end 0");
        }

        // Validate stock quantity
        if (dto.getStockQuantity() == null || dto.getStockQuantity() < 0) {
            throw new IllegalArgumentException("Lager antal skal være 0 eller større");
        }

        // Validate name
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Produkt navn er påkrævet");
        }

        // Validate size
        if (dto.getSize() == null || dto.getSize().trim().isEmpty()) {
            throw new IllegalArgumentException("Størrelse er påkrævet");
        }
        try {
            Size.valueOf(dto.getSize().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ugyldig størrelse. Tilladte værdier: XS, S, M, L, XL, XXL");
        }

        // Validate color
        if (dto.getColor() == null || dto.getColor().trim().isEmpty()) {
            throw new IllegalArgumentException("Farve er påkrævet");
        }
        try {
            Color.valueOf(dto.getColor().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ugyldig farve. Tilladte værdier: RED, BLUE, GREEN, BLACK, WHITE, YELLOW, ORANGE, PURPLE, PINK, GRAY");
        }
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
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Produkt med id: " + id + " ikke fundet."));
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
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(productDetails.getName());
                    product.setDescription(productDetails.getDescription());
                    product.setPrice(productDetails.getPrice());
                    product.setImageUrl(productDetails.getImageUrl());
                    product.setSize(productDetails.getSize());
                    product.setColor(productDetails.getColor());
                    product.setStockQuantity(productDetails.getStockQuantity());
                    product.setActive(productDetails.getActive());
                    return productRepository.save(product);
                })
                .orElseThrow(() ->
                        new ResourceNotFoundException("Produkt med id: " + id + " ikke fundet."));
    }

    // Deactivate product (soft delete - doesn't affect historical orders)
    public Product deactivateProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Produkt med id: " + id + " ikke fundet."));

        product.setActive(false);
        return productRepository.save(product);
    }

    // Activate product
    public Product activateProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Produkt med id: " + id + " ikke fundet."));

        product.setActive(true);
        return productRepository.save(product);
    }

    // Hard delete (for testing/admin purposes only - be careful with this!)
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produkt med id: " + id + " ikke fundet.");
        }
        productRepository.deleteById(id);
    }
}
