package dk.ss.backendtshirt.tshirt.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_amount")
    private BigDecimal totalAmount = BigDecimal.ZERO; // Starter på 0

    @Column(name = "created_at")
    private LocalDateTime created_At;

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
        this.created_At = LocalDateTime.now(); // Sætter tidspunkt automatisk
    }

    // 2. All-Args Constructor (Bruges sjældent manuelt, men god at have)
    public Cart(Long id, BigDecimal totalAmount, LocalDateTime created_At) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.created_At = created_At;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getCreated_At() {
        return created_At;
    }

    public void setCreated_At(LocalDateTime created_At) {
        this.created_At = created_At;
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
