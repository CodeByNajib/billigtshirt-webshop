package dk.ss.backendtshirt.tshirt.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // Bruger "orders" for at undgå SQL - fejl
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "total_amount" )
    private BigDecimal totalAmount;

    @Column(name = "original_gift_threshold")
    private BigDecimal originalGiftThreshold;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    // Relation til Customer
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Relation til OrderGift - En ordre kan teknisk set have gaver
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderGift> orderGifts = new ArrayList<>();


    public Order() {
    }

    public Order(int id, String orderNumber,
                 BigDecimal totalAmount,
                 BigDecimal originalGiftThreshold,
                 LocalDateTime orderDate,
                 Customer customer,
                 List<OrderGift> orderGifts) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.totalAmount = totalAmount;
        this.originalGiftThreshold = originalGiftThreshold;
        this.orderDate = orderDate;
        this.customer = customer;
        this.orderGifts = orderGifts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getOriginalGiftThreshold() {
        return originalGiftThreshold;
    }

    public void setOriginalGiftThreshold(BigDecimal originalGiftThreshold) {
        this.originalGiftThreshold = originalGiftThreshold;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderGift> getOrderGifts() {
        return orderGifts;
    }

    public void setOrderGifts(List<OrderGift> orderGifts) {
        this.orderGifts = orderGifts;
    }

    // Hjælper-metode til at tilføje en gave nemt
    public void addGift(OrderGift gift) {
        orderGifts.add(gift);
        gift.setOrder(this);
    }
}
