package dk.ss.backendtshirt.tshirt.dto;

import java.math.BigDecimal;

public class OrderItemDTO {
    private String productName;
    private Integer quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal rowTotal;

    public OrderItemDTO() {
    }

    public OrderItemDTO(String productName, Integer quantity, BigDecimal pricePerUnit, BigDecimal rowTotal) {
        this.productName = productName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.rowTotal = rowTotal;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public BigDecimal getRowTotal() {
        return rowTotal;
    }

    public void setRowTotal(BigDecimal rowTotal) {
        this.rowTotal = rowTotal;
    }
}

