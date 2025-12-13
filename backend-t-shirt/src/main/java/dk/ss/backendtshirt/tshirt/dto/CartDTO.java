package dk.ss.backendtshirt.tshirt.dto;

import java.math.BigDecimal;
import java.util.List;

/** contains the list of rows (Cart items) and the final calculation **/
public class CartDTO {
    private List<CartItemDTO> items;
    private BigDecimal grandTotal;


    // Fields for User Story US-K1 (Free Gift)
    private BigDecimal missingForFreeGift;
    private boolean canSelectFreeGift;
    private boolean hasFreeGift;
    private FreeGiftDTO freeGift;

    public CartDTO(List<CartItemDTO> items, BigDecimal grandTotal) {
        this.items = items;
        this.grandTotal = grandTotal;
    }

    // Getters & Setters
    public List<CartItemDTO> getItems() { return items; }
    public BigDecimal getGrandTotal() { return grandTotal; }

    public BigDecimal getMissingForFreeGift() { return missingForFreeGift; }
    public void setMissingForFreeGift(BigDecimal missingForFreeGift) { this.missingForFreeGift = missingForFreeGift; }

    public boolean isCanSelectFreeGift() { return canSelectFreeGift; }
    public void setCanSelectFreeGift(boolean canSelectFreeGift) { this.canSelectFreeGift = canSelectFreeGift; }

    public boolean isHasFreeGift() { return hasFreeGift; }
    public void setHasFreeGift(boolean hasFreeGift) { this.hasFreeGift = hasFreeGift; }

    public FreeGiftDTO getFreeGift() { return freeGift; }
    public void setFreeGift(FreeGiftDTO freeGift) { this.freeGift = freeGift; }
}
