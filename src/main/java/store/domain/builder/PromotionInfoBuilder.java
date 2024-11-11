package store.domain.builder;

import java.time.LocalDateTime;
import store.domain.PromotionInfo;

public class PromotionInfoBuilder {
    private String title;
    private int requiredQuantity;
    private int presentQuantity;
    private LocalDateTime startDate;
    private LocalDateTime endDate;


    public PromotionInfoBuilder title(String title) {
        this.title = title;
        return this;
    }

    public PromotionInfoBuilder requiredQuantity(int requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
        return this;
    }

    public PromotionInfoBuilder presentQuantity(int presentQuantity) {
        this.presentQuantity = presentQuantity;
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
        return new PromotionInfo(title, requiredQuantity, presentQuantity, startDate, endDate);
    }
}
