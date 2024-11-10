package store.domain;

import java.time.LocalDateTime;

public class PromotionItem extends Item {

    private final PromotionInfo promotionInfo;

    public PromotionItem(String name, int price, int stock, PromotionInfo promotionInfo) {
        super(name, price, stock);
        this.promotionInfo = promotionInfo;
    }

    public int getDiscountedPrice(int amount) {
        return price * amount;
    }

    public boolean canApplyPromotionNow(LocalDateTime date) {
        return promotionInfo.isAvailable(date);
    }

    public int getLeftAmountForPromotion(int amount) {
        return promotionInfo.getLeftAmountForPromotion(amount);
    }

    public int getPresentableAmount(int amount) {
        return promotionInfo.getPresentableAmount(amount);
    }

    public int getAmountToNotApplyPromotion(int amount) {
        return amount - (stock - promotionInfo.getLeftAmountForPromotion(stock));
    }

    @Override
    public String toString() {
        // - 콜라 1,000원 10개 탄산2+1
        return "-" + name + " " + price + "원 " + stock + "개 " + promotionInfo.getTitle();
    }
}
