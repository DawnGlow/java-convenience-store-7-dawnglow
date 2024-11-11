package store.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 주문 처리 후 분류된 주문 항목들을 담는 클래스
 */
public class CategorizedOrderItems {
    private List<OrderItemDto> normalOrderItemDtos;
    private List<OrderItemDto> promotionOrderItemDtos;

    public CategorizedOrderItems() {
        this.normalOrderItemDtos = new ArrayList<>();
        this.promotionOrderItemDtos = new ArrayList<>();
    }

    public List<OrderItemDto> getNormalOrderItemDtos() {
        return normalOrderItemDtos;
    }

    public List<OrderItemDto> getPromotionOrderItemDtos() {
        return promotionOrderItemDtos;
    }

    public void addNormalItem(OrderItemDto item) {
        this.normalOrderItemDtos.add(item);
    }

    public void addPromotionItem(OrderItemDto item) {
        this.promotionOrderItemDtos.add(item);
    }
}
