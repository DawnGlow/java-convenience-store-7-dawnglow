package store.service;

import java.util.ArrayList;
import java.util.List;
import store.domain.MembershipGrade;
import store.dto.CategorizedOrderItems;
import store.dto.OrderItemDto;
import store.dto.OrderResponseDto;
import store.repository.ItemRepository;
import store.repository.PromotionItemRepository;
import store.repository.PromotionRepository;

public class OrderService {
    private final ItemRepository itemRepository;
    private final PromotionItemRepository promotionItemRepository;
    private final PromotionRepository promotionRepository;

    public OrderService(ItemRepository itemRepository, PromotionItemRepository promotionItemRepository,
                        PromotionRepository promotionRepository) {
        this.itemRepository = itemRepository;
        this.promotionItemRepository = promotionItemRepository;
        this.promotionRepository = promotionRepository;
    }


    public OrderResponseDto processOrder(List<OrderItemDto> orderItemDtos,
                                         CategorizedOrderItems categorizedOrderItems,
                                         MembershipGrade membershipGrade) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderItemDtos.forEach(this::calculateEachPrice);
        orderResponseDto.setTotalOrderItemDtos(orderItemDtos);
        categorizedOrderItems.getPromotionOrderItemDtos().forEach(orderItemDto -> {
            OrderItemDto promotionItemInfo = getPromotionItemInfo(orderItemDto);
            orderResponseDto.addPromotionOrderItem(promotionItemInfo);
        });
        int totalPrice = orderItemDtos.stream()
                .mapToInt(OrderItemDto::getPrice)
                .sum();
        int discountedPriceByPromotion = orderResponseDto.getPromotionOrderItemDtos().stream()
                .mapToInt(OrderItemDto::getPrice)
                .sum();

        List<String> promotionItemsName = new ArrayList<>();
        for (OrderItemDto orderItemDto : categorizedOrderItems.getPromotionOrderItemDtos()) {
            promotionItemsName.add(orderItemDto.getName());
        }
        int val = 0;
        for (OrderItemDto orderItemDto : orderItemDtos) {
            if (promotionItemsName.contains(orderItemDto.getName())) {
                val += orderItemDto.getPrice();
            }
        }

        int memberShipDiscountPrice = membershipGrade.getDiscountPrice(totalPrice - val);
        orderResponseDto.setTotalPrice(totalPrice);
        orderResponseDto.setPromotionDiscountPrice(discountedPriceByPromotion);
        orderResponseDto.setMemberShipDiscountPrice(memberShipDiscountPrice);
        orderResponseDto.setFinalPrice(totalPrice - discountedPriceByPromotion - memberShipDiscountPrice);
        decreaseStock(categorizedOrderItems);
        return orderResponseDto;
    }

    private void calculateEachPrice(OrderItemDto orderItemDto) {
        orderItemDto.setPrice(itemRepository.findByName(orderItemDto.getName()).getFirst()
                .getPriceByQuantity(orderItemDto.getQuantity()));
    }

    private OrderItemDto getPromotionItemInfo(OrderItemDto orderItemDto) {
        int presentableQuantity = promotionItemRepository.findByName(orderItemDto.getName()).getFirst()
                .getPresentableQuantity(orderItemDto.getQuantity());
        int discountedPrice = promotionItemRepository.findByName(orderItemDto.getName()).getFirst()
                .getDiscountedPriceByQuantity(presentableQuantity);
        return new OrderItemDto(orderItemDto.getName(), presentableQuantity, discountedPrice);
    }

    private void decreaseStock(CategorizedOrderItems categorizedOrderItems) {
        categorizedOrderItems.getNormalOrderItemDtos().forEach(orderItemDto -> {
            itemRepository.findByName(orderItemDto.getName()).getFirst()
                    .decreaseStock(orderItemDto.getQuantity());
        });
        categorizedOrderItems.getPromotionOrderItemDtos().forEach(orderItemDto -> {
            promotionItemRepository.findByName(orderItemDto.getName()).getFirst()
                    .decreaseStock(orderItemDto.getQuantity());
        });
    }
}
