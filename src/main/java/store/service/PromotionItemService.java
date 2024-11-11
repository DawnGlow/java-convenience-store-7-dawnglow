package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.List;
import store.domain.PromotionItem;
import store.dto.OrderItemDto;
import store.repository.PromotionItemRepository;

public class PromotionItemService {

    private final PromotionItemRepository promotionItemRepository;

    public PromotionItemService(PromotionItemRepository promotionItemRepository) {
        this.promotionItemRepository = promotionItemRepository;
    }

    public List<String> findAllInformation() {
        return promotionItemRepository.findAll().stream()
                .map(PromotionItem::toString)
                .toList();
    }

    public boolean canApplyPromotionNow(OrderItemDto orderItemDto) {
        List<PromotionItem> promotionItems = promotionItemRepository.findByName(orderItemDto.getName());
        if (promotionItems.isEmpty() || !promotionItems.getFirst().canApplyPromotionNow(DateTimes.now())) {
            return false;
        }
        return true;
    }

    public int getLeftQuantityForPromotion(OrderItemDto orderItemDto) {
        PromotionItem promotionItem = promotionItemRepository.findByName(orderItemDto.getName()).getFirst();
        return promotionItem.getLeftQuantityForPromotion(orderItemDto.getQuantity());
    }

    public int getPresentableQuantityIfBuyMore(OrderItemDto orderItemDto) {
        PromotionItem promotionItem = promotionItemRepository.findByName(orderItemDto.getName()).getFirst();
        return promotionItem.getPresentableQuantity(
                orderItemDto.getQuantity() + promotionItem.getLeftQuantityForPromotion(orderItemDto.getQuantity())
                        - promotionItem.getPromotionApplyQuantity(orderItemDto.getQuantity()));
    }

    public int getQuantityToApplyPromotion(OrderItemDto orderItemDto) {
        PromotionItem promotionItem = promotionItemRepository.findByName(orderItemDto.getName()).getFirst();
        return promotionItem.getPromotionApplyQuantity(orderItemDto.getQuantity());
    }

    public int getQuantityToNotApplyPromotion(OrderItemDto orderItemDto) {
        PromotionItem promotionItem = promotionItemRepository.findByName(orderItemDto.getName()).getFirst();
        return promotionItem.getQuantityToNotApplyPromotion(orderItemDto.getQuantity());
    }

    public boolean isInsufficientStockForPromotion(OrderItemDto orderItemDto) {
        PromotionItem promotionItem = promotionItemRepository.findByName(orderItemDto.getName()).getFirst();
        return promotionItem.checkInSufficientStock(orderItemDto.getQuantity());
    }

    public int findStockByName(OrderItemDto orderItemDto) {
        if (promotionItemRepository.findByName(orderItemDto.getName()).isEmpty()) {
            return 0;
        }
        return promotionItemRepository.findByName(orderItemDto.getName()).getFirst().getStock();
    }
}
