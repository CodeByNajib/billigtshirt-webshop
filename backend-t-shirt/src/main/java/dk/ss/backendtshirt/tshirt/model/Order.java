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
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "total_amount" )
    private BigDecimal totalAmount;

    @Column(name = "original_gift_threshold")
    private BigDecimal originalGiftThreshold;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    // Direct customer information fields for checkout
    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "notes")
    private String notes;

    // Relation til Customer (optional - for registered users)
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Relation til OrderGift - En ordre kan teknisk set have gaver
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderGift> orderGifts = new ArrayList<>();


    public Order() {
    }

    public Order(Long id, String orderNumber,
                 BigDecimal totalAmount,
                 BigDecimal originalGiftThreshold,
                 LocalDateTime orderDate,
                 String customerName,
                 String customerEmail,
                 String customerPhone,
                 String deliveryAddress,
                 String notes,
                 Customer customer,
                 List<OrderGift> orderGifts) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.totalAmount = totalAmount;
        this.originalGiftThreshold = originalGiftThreshold;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.deliveryAddress = deliveryAddress;
        this.notes = notes;
        this.customer = customer;
        this.orderGifts = orderGifts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
