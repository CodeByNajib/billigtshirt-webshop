package dk.ss.backendtshirt.tshirt.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ORDER_GIFT")
public class OrderGift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Denne binder gaven til ordren
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "gift_product_id")
    private int giftProductId;  // ID på det fysiske produkt (kop eller bamse)

    private int available; // 1 eller 0


    public OrderGift() {
    }

    public OrderGift(int id, Order order, int giftProductId, int available) {
        this.id = id;
        this.order = order;
        this.giftProductId = giftProductId;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getGiftProductId() {
        return giftProductId;
    }

    public void setGiftProductId(int giftProductId) {
        this.giftProductId = giftProductId;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}
