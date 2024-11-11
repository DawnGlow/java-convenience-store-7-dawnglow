package store.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import store.domain.PromotionItem;

public class PromotionItemRepository {
    private final List<PromotionItem> promotionItems = new ArrayList<>();

    public void save(PromotionItem promotionItem) {
        promotionItems.add(promotionItem);
    }

    public List<PromotionItem> findByName(String name) {
        return promotionItems.stream()
                .filter(promotionItem -> promotionItem.getName().equals(name))
                .collect(Collectors.toList());
    }

    public List<PromotionItem> findAll() {
        return promotionItems;
    }
    
}
