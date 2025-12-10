package dk.ss.backendtshirt.tshirt.dto;

import dk.ss.backendtshirt.tshirt.model.GiftProduct;

/**
 * DTO for gift product responses - returns active as INTEGER (1 or 0)
 */
public class GiftProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Integer stockQuantity;
    private String imageUrl;
    private Integer active; // INTEGER: 1 = active, 0 = inactive

    public GiftProductResponseDTO() {
    }

    public GiftProductResponseDTO(GiftProduct giftProduct) {
        this.id = giftProduct.getId();
        this.name = giftProduct.getName();
        this.description = giftProduct.getDescription();
        this.stockQuantity = giftProduct.getStockQuantity();
        this.imageUrl = giftProduct.getImageUrl();
        // Convert Boolean to Integer: true -> 1, false -> 0
        this.active = (giftProduct.getActive() != null && giftProduct.getActive()) ? 1 : 0;
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

