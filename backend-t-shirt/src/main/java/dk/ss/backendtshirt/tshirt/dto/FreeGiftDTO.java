package dk.ss.backendtshirt.tshirt.dto;

/**
 * DTO for free gift information in cart
 */
public class FreeGiftDTO {
    private String productName;
    private Long productId;

    public FreeGiftDTO() {
    }

    public FreeGiftDTO(String productName, Long productId) {
        this.productName = productName;
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}

