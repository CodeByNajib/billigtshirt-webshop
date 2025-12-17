package dk.ss.backendtshirt.tshirt.model;

import dk.ss.backendtshirt.common.model.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart extends BaseEntity {


    @Column(name = "total_amount")
    private BigDecimal totalAmount = BigDecimal.ZERO; // Starter på 0


    // Felt til at holde styr på valgt gratis gave
    @Column(name = "free_gift_product_id")
    private Long freeGiftProductId;

    // Relationen til produkterne
    @ManyToMany
    @JoinTable(
            name = "cart_items",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> items = new ArrayList<>();

    // --- CONSTRUCTORS ---

    // 1. No-Args Constructor (Bruges når man laver en ny tom kurv)
    public Cart() {
    }

    // 2. All-Args Constructor (Bruges sjældent manuelt, men god at have)
    public Cart( BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    // --- HJÆLPEMETODER (Logik til kurven) ---

    public void addItem(Product product) {
        items.add(product);
        recalculateTotal();
    }

    public void removeItem(Product product) {
        items.remove(product);
        recalculateTotal();
    }

    // Genberegner totalbeløbet baseret på varerne i listen
    public void recalculateTotal() {
        if (items != null) {
            this.totalAmount = items.stream()
                    .map(Product::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    // --- GETTERS & SETTERS ---
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }


    public Long getFreeGiftProductId() {
        return freeGiftProductId;
    }

    public void setFreeGiftProductId(Long freeGiftProductId) {
        this.freeGiftProductId = freeGiftProductId;
    }

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
        recalculateTotal(); // Opdater totalen hvis vi sætter en ny liste
    }
}
