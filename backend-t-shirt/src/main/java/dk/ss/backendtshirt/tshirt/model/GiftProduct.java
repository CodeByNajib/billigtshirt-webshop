package dk.ss.backendtshirt.tshirt.model;

import dk.ss.backendtshirt.common.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Table(name = "gift_products")
public class GiftProduct extends BaseEntity {

    @NotBlank(message = "Gift product name is required")
    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @NotBlank(message = "Size is required")
    @Column(nullable = false)
    private String size;

    @NotBlank(message = "Color is required")
    @Column(nullable = false)
    private String color;

    @NotNull(message = "Stock quantity is required")
    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false)
    private Boolean active = true;

    // Constructors
    public GiftProduct() {
    }

    public GiftProduct(String name, String description, String imageUrl,
                       String size, String color, Integer stockQuantity, Boolean active) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.size = size;
        this.color = color;
        this.stockQuantity = stockQuantity;
        this.active = active;
    }

    // Getters and Setters
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

