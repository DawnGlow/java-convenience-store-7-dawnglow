package store.domain.builder;

import store.domain.PromotionInfo;
import store.domain.PromotionItem;

public class PromotionItemBuilder {
    private String name;
    private int price;
    private int stock;
    private PromotionInfo promotionInfo;

    public PromotionItemBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PromotionItemBuilder price(int price) {
        this.price = price;
        return this;
    }

    public PromotionItemBuilder stock(int stock) {
        this.stock = stock;
        return this;
    }

    public PromotionItemBuilder promotionInfo(PromotionInfo promotionInfo) {
        this.promotionInfo = promotionInfo;
        return this;
    }

    public PromotionItem build() {
        return new PromotionItem(name, price, stock, promotionInfo);
    }
}