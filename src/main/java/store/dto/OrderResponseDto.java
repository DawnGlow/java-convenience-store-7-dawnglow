package store.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderResponseDto {
    List<OrderItemDto> totalOrderItemDtos;
    List<OrderItemDto> promotionOrderItemDtos = new ArrayList<>();

    private int totalPrice;

    private int promotionDiscountPrice;

    private int memberShipDiscountPrice;

    private int finalPrice;

    public List<OrderItemDto> getTotalOrderItemDtos() {
        return totalOrderItemDtos;
    }

    public void setTotalOrderItemDtos(List<OrderItemDto> totalOrderItemDtos) {
        this.totalOrderItemDtos = totalOrderItemDtos;
    }

    public List<OrderItemDto> getPromotionOrderItemDtos() {
        return promotionOrderItemDtos;
    }

    public void setPromotionOrderItemDtos(List<OrderItemDto> promotionOrderItemDtos) {
        this.promotionOrderItemDtos = promotionOrderItemDtos;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getPromotionDiscountPrice() {
        return promotionDiscountPrice;
    }

    public void setPromotionDiscountPrice(int promotionDiscountPrice) {
        this.promotionDiscountPrice = promotionDiscountPrice;
    }

    public int getMemberShipDiscountPrice() {
        return memberShipDiscountPrice;
    }

    public void setMemberShipDiscountPrice(int memberShipDiscountPrice) {
        this.memberShipDiscountPrice = memberShipDiscountPrice;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public void addPromotionOrderItem(OrderItemDto orderItemDto) {
        promotionOrderItemDtos.add(orderItemDto);
    }
}
