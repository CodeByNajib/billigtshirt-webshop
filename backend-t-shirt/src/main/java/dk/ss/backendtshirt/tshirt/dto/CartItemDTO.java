package dk.ss.backendtshirt.tshirt.dto;

import java.math.BigDecimal;

/** TASK 1.1 – Backend: Kurv-DTO og Service
 * represents one "row" in the cart UI (e.g., "2x White T-Shirt") **/
public class CartItemDTO {
    private Long productId;        // Product's ID - KRITISK!
    private String productName;
    private int quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal rowTotal; // quantity * price

    public CartItemDTO(Long productId, String productName, int quantity, BigDecimal pricePerUnit) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.rowTotal = pricePerUnit.multiply(BigDecimal.valueOf(quantity));
    }

    // Getters
    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPricePerUnit() { return pricePerUnit; }
    public BigDecimal getRowTotal() { return rowTotal; }
}
