package dk.ss.backendtshirt.tshirt.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ORDER_GIFT")
public class OrderGift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Denne binder gaven til ordren
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "gift_product_id")
    private Long giftProductId;  // ID på det fysiske produkt (kop eller bamse)

    private int available; // 1 eller 0


    public OrderGift() {
    }

    public OrderGift(Long id, Order order, Long giftProductId, int available) {
        this.id = id;
        this.order = order;
        this.giftProductId = giftProductId;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getGiftProductId() {
        return giftProductId;
    }

    public void setGiftProductId(Long giftProductId) {
        this.giftProductId = giftProductId;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}
