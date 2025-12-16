package dk.ss.backendtshirt.tshirt.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "gift_config")
public class GiftConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "threshold_amount")
    private BigDecimal thresholdAmount;

    @Column(name = "gift_product_id")
    private Long giftProductId;

    private int active; // 1 = active, 0 = inactive

    public GiftConfig() {
    }

    public GiftConfig(Long id, BigDecimal thresholdAmount, int active, Long giftProductId) {
        this.id = id;
        this.thresholdAmount = thresholdAmount;
        this.active = active;
        this.giftProductId = giftProductId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getThresholdAmount() {
        return thresholdAmount;
    }

    public void setThresholdAmount(BigDecimal thresholdAmount) {
        this.thresholdAmount = thresholdAmount;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Long getGiftProductId() {
        return giftProductId;
    }

    public void setGiftProductId(Long giftProductId) {
        this.giftProductId = giftProductId;
    }
}
