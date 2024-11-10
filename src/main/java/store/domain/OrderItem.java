package store.domain;

import java.time.LocalDateTime;

public class OrderItem {
    private final Item item;
    private final int count;

    public OrderItem(Item item, int count) {
        this.item = item;
        this.count = count;
    }

    public int calculateOriginPrice() {
        return item.getPriceByAmount(count);
    }

    public void decreaseStock() {
        item.decreaseStock(count);
    }

    public int getDiscountPrice() {
        if (item instanceof PromotionItem) {
            PromotionItem promotionItem = (PromotionItem) item;
            return promotionItem.getDiscountedPrice(count);
        }
        return 0;
    }

    public int getPresentableAmount() {
        if (item instanceof PromotionItem) {
            PromotionItem promotionItem = (PromotionItem) item;
            return promotionItem.getPresentableAmount(count);
        }
        return 0;
    }

    public int getAmountToNotApplyPromotion() {
        if (item instanceof PromotionItem) {
            PromotionItem promotionItem = (PromotionItem) item;
            return promotionItem.getAmountToNotApplyPromotion(count);
        }
        return 0;
    }

    public boolean canApplyPromotionNow(LocalDateTime date) {
        if (item instanceof PromotionItem) {
            PromotionItem promotionItem = (PromotionItem) item;
            return promotionItem.canApplyPromotionNow(date);
        }
        return false;
    }

    public int getLeftAmountForPromotion() {
        if (item instanceof PromotionItem) {
            PromotionItem promotionItem = (PromotionItem) item;
            return promotionItem.getLeftAmountForPromotion(count);
        }
        return 0;
    }
}
