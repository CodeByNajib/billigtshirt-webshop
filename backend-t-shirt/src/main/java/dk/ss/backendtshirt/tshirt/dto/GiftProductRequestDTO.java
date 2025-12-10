package dk.ss.backendtshirt.tshirt.dto;

/**
 * DTO for gift product update/create requests - accepts active as BOOLEAN (true/false)
 */
public class GiftProductRequestDTO {
    private String name;
    private String description;
    private Integer stockQuantity;
    private String imageUrl;
    private Boolean active; // BOOLEAN: true or false (will be converted to 1/0 in database)

    public GiftProductRequestDTO() {
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

