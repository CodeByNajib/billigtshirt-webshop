package dk.ss.backendtshirt.tshirt.dto;

public class GiftRemovedResponse {
    private boolean giftRemoved;
    private String removedGiftName;
    private String message;
    private CartDTO updatedCart;

    public GiftRemovedResponse() {
    }

    public GiftRemovedResponse(boolean giftRemoved, String removedGiftName,
                              String message, CartDTO updatedCart) {
        this.giftRemoved = giftRemoved;
        this.removedGiftName = removedGiftName;
        this.message = message;
        this.updatedCart = updatedCart;
    }

    public boolean isGiftRemoved() {
        return giftRemoved;
    }

    public void setGiftRemoved(boolean giftRemoved) {
        this.giftRemoved = giftRemoved;
    }

    public String getRemovedGiftName() {
        return removedGiftName;
    }

    public void setRemovedGiftName(String removedGiftName) {
        this.removedGiftName = removedGiftName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CartDTO getUpdatedCart() {
        return updatedCart;
    }

    public void setUpdatedCart(CartDTO updatedCart) {
        this.updatedCart = updatedCart;
    }
}


