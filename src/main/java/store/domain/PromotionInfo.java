package store.domain;

import java.time.LocalDateTime;

public class PromotionInfo {
    private final String title;
    private final int requiredQuantity;
    private final int presentQuantity;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public PromotionInfo(String title, int requiredQuantity, int presentQuantity, LocalDateTime startDate,
                         LocalDateTime endDate) {
        this.title = title;
        this.requiredQuantity = requiredQuantity;
        this.presentQuantity = presentQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isAvailable(LocalDateTime date) {
        return startDate.isBefore(date) && endDate.isAfter(date);
    }

    public int getLeftQuantityForPromotion(int quantity) {
        return quantity % requiredQuantity;
    }

    public int getPresentableQuantity(int quantity) {
        return (quantity / requiredQuantity) * presentQuantity;
    }

    public int getPromotionApplyQuantityFromStock(int quantity) {
        return (quantity / requiredQuantity) * requiredQuantity;
    }

    public String getTitle() {
        return title;
    }
}
