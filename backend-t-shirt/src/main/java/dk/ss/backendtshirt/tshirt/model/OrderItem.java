package dk.ss.backendtshirt.tshirt.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dk.ss.backendtshirt.common.model.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {
    @ManyToOne
    @JsonBackReference // Forhindrer uendelig løkke i JSON
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "product_name", nullable = false)
    private String productName;

    private int quantity;

    @Column(name = "price_per_unit", nullable = false)
    private BigDecimal pricePerUnit;

    @Column(name = "row_total", nullable = false)
    private BigDecimal rowTotal;

    @Column(name = "is_free_gift")
    private boolean isFreeGift = false;

    public OrderItem() {}

    // Getters og Setters
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getPricePerUnit() { return pricePerUnit; }
    public void setPricePerUnit(BigDecimal pricePerUnit) { this.pricePerUnit = pricePerUnit; }

    public BigDecimal getRowTotal() { return rowTotal; }
    public void setRowTotal(BigDecimal rowTotal) { this.rowTotal = rowTotal; }

    public boolean isFreeGift() { return isFreeGift; }
    public void setFreeGift(boolean freeGift) { isFreeGift = freeGift; }
}
