package dk.ss.backendtshirt.tshirt.dto;

import java.math.BigDecimal;

public class GiftConfigResponseDTO {
    private Long id;
    private BigDecimal thresholdAmount;
    private Long giftProductId;
    private String giftProductName;
    private Integer active;

    public GiftConfigResponseDTO() {
    }

    public GiftConfigResponseDTO(Long id, BigDecimal thresholdAmount, Long giftProductId, String giftProductName, Integer active) {
        this.id = id;
        this.thresholdAmount = thresholdAmount;
        this.giftProductId = giftProductId;
        this.giftProductName = giftProductName;
        this.active = active;
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

    public Long getGiftProductId() {
        return giftProductId;
    }

    public void setGiftProductId(Long giftProductId) {
        this.giftProductId = giftProductId;
    }

    public String getGiftProductName() {
        return giftProductName;
    }

    public void setGiftProductName(String giftProductName) {
        this.giftProductName = giftProductName;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
}

