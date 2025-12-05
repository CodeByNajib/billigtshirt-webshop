package dk.ss.backendtshirt.tshirt.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "GIFT_CONFIG")
public class GiftConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "threshold_amount")
    private BigDecimal thresholdAmount;

    private int active; // 1 = active, 0 = inactive

    public GiftConfig() {
    }

    public GiftConfig(int id, BigDecimal thresholdAmount, int active) {
        this.id = id;
        this.thresholdAmount = thresholdAmount;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
