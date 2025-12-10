package dk.ss.backendtshirt.tshirt.dto;

import java.math.BigDecimal;

public class GiftConfigRequest {
    private BigDecimal thresholdAmount;
    private Long giftProductId;
    private Integer active;

    public GiftConfigRequest() {
    }

    public GiftConfigRequest(BigDecimal thresholdAmount, Long giftProductId, Integer active) {
        this.thresholdAmount = thresholdAmount;
        this.giftProductId = giftProductId;
        this.active = active;
    }

    public BigDecimal getThresholdAmount() {
        return thresholdAmount;
    }

    public void setThresholdAmount(BigDecimal thresholdAmount) {
        this.thresholdAmount = thresholdAmount;
    }

    public Long getGiftProductId() {
        return giftProductId;
    }

    public void setGiftProductId(Long giftProductId) {
        this.giftProductId = giftProductId;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
}

