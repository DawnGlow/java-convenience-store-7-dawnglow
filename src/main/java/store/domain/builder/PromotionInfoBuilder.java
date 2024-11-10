package store.domain.builder;

import java.time.LocalDateTime;
import store.domain.PromotionInfo;

public class PromotionInfoBuilder {
    private String title;
    private int requiredAmount;
    private int presentAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;


    public PromotionInfoBuilder title(String title) {
        this.title = title;
        return this;
    }

    public PromotionInfoBuilder requiredAmount(int requiredAmount) {
        this.requiredAmount = requiredAmount;
        return this;
    }

    public PromotionInfoBuilder presentAmount(int presentAmount) {
        this.presentAmount = presentAmount;
        return this;
    }

    public PromotionInfoBuilder startDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public PromotionInfoBuilder endDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public PromotionInfo build() {
        return new PromotionInfo(title, requiredAmount, presentAmount, startDate, endDate);
    }
}
