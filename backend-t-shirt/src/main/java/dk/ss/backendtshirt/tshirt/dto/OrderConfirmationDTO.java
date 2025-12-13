package dk.ss.backendtshirt.tshirt.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderConfirmationDTO {
    private Long orderId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String deliveryAddress;
    private List<OrderItemDTO> items;
    private FreeGiftDTO freeGift;  // Can be null
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;

    public OrderConfirmationDTO() {
    }

    public OrderConfirmationDTO(Long orderId, String customerName, String customerEmail,
                               String customerPhone, String deliveryAddress,
                               List<OrderItemDTO> items, FreeGiftDTO freeGift,
                               BigDecimal totalAmount, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.deliveryAddress = deliveryAddress;
        this.items = items;
        this.freeGift = freeGift;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public FreeGiftDTO getFreeGift() {
        return freeGift;
    }

    public void setFreeGift(FreeGiftDTO freeGift) {
        this.freeGift = freeGift;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}

