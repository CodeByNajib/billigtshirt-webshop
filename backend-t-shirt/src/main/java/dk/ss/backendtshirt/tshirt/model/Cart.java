package dk.ss.backendtshirt.tshirt.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "CART")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "create_at")
    private LocalDateTime created_At;

    public Cart() {
    }

    public Cart(int id, BigDecimal totalAmount, LocalDateTime created_At) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.created_At = created_At;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
