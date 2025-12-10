package dk.ss.backendtshirt.tshirt.dto;

import java.math.BigDecimal;

/** TASK 1.1 – Backend: Kurv-DTO og Service
 * represents one "row" in the cart UI (e.g., "2x White T-Shirt") **/
public class CartItemDTO {
    private String productName;
    private int quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal rowTotal; // quantity * price

    public CartItemDTO(String productName, int quantity, BigDecimal pricePerUnit) {
        this.productName = productName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.rowTotal = pricePerUnit.multiply(BigDecimal.valueOf(quantity));
    }

    // Getters
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPricePerUnit() { return pricePerUnit; }
    public BigDecimal getRowTotal() { return rowTotal; }
}
