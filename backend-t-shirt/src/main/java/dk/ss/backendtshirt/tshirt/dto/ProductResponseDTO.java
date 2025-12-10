package dk.ss.backendtshirt.tshirt.dto;

import dk.ss.backendtshirt.tshirt.model.Product;

import java.math.BigDecimal;

/**
 * DTO for product responses - returns active as INTEGER (1 or 0)
 */
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String size;
    private String color;
    private Integer stockQuantity;
    private String imageUrl;
    private Integer active; // INTEGER: 1 = active, 0 = inactive

    public ProductResponseDTO() {
    }

    public ProductResponseDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.size = product.getSize() != null ? product.getSize().name() : null;
        this.color = product.getColor() != null ? product.getColor().name() : null;
        this.stockQuantity = product.getStockQuantity();
        this.imageUrl = product.getImageUrl();
        // Convert Boolean to Integer: true -> 1, false -> 0
        this.active = (product.getActive() != null && product.getActive()) ? 1 : 0;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
}

