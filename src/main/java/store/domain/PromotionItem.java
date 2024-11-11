package store.domain;

import java.time.LocalDateTime;

public class PromotionItem extends Item {
    private final PromotionInfo promotionInfo;

    public PromotionItem(String name, int price, int stock, PromotionInfo promotionInfo) {
        super(name, price, stock);
        this.promotionInfo = promotionInfo;
    }

    public int getDiscountedPriceByQuantity(int quantity) {
        return price * quantity;
    }

    public int getPresentableQuantity(int quantity) {
        return promotionInfo.getPresentableQuantity(quantity);
    }

    public int getPromotionApplyQuantity(int quantity) {
        return promotionInfo.getPromotionApplyQuantityFromStock(quantity);
    }

    public boolean canApplyPromotionNow(LocalDateTime date) {
        return promotionInfo.isAvailable(date);
    }

    public boolean checkInSufficientStock(int quantity) {
        return quantity > promotionInfo.getPromotionApplyQuantityFromStock(stock);
    }

    public int getLeftQuantityForPromotion(int quantity) {
        return promotionInfo.getLeftQuantityForPromotion(quantity);
    }


    public int getQuantityToNotApplyPromotion(int quantity) {
        return quantity - promotionInfo.getPromotionApplyQuantityFromStock(stock);
    }

    @Override
    public String toString() {
        String stockInfo = (stock > 0) ? stock + "개" : "재고 없음";
        return "- " + name + " " + formatPrice(price) + " " + stockInfo + " " + promotionInfo.getTitle();
    }
}