package store.domain;

import java.time.LocalDateTime;

public class PromotionInfo {
    private final String title;
    private final int requiredAmount;
    private final int presentAmount;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public PromotionInfo(String title, int requiredAmount, int presentAmount, LocalDateTime startDate,
                         LocalDateTime endDate) {
        this.title = title;
        this.requiredAmount = requiredAmount;
        this.presentAmount = presentAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isAvailable(LocalDateTime date) {
        return startDate.isBefore(date) && endDate.isAfter(date);
    }

    public int getLeftAmountForPromotion(int amount) {
        return amount % (requiredAmount + presentAmount);
    }

    public int getPresentableAmount(int amount) {
        return amount / (requiredAmount + presentAmount);
    }

    public String getTitle() {
        return title;
    }
}
