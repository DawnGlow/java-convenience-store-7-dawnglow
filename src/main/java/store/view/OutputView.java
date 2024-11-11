package store.view;

import java.util.List;
import store.dto.OrderItemDto;
import store.dto.OrderResponseDto;

public class OutputView {
    public void showWelcomeMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
    }

    public void showItems(List<String> itemsInformation) {
        System.out.println("현재 보유하고 있는 상품입니다.");
        System.out.println();
        itemsInformation.forEach(System.out::println);
        System.out.println();
    }

    public void showOrderResult(OrderResponseDto orderResponseDto) {
        System.out.println("==============W 편의점================");
        System.out.printf("%-15s %-5s %-10s%n", "상품명", "수량", "금액");

        for (OrderItemDto orderItemDto : orderResponseDto.getTotalOrderItemDtos()) {
            String formattedPrice = String.format("%,d", orderItemDto.getPrice());
            System.out.printf("%-15s %-5d %10s%n",
                    orderItemDto.getName(),
                    orderItemDto.getQuantity(),
                    formattedPrice);
        }

        System.out.println("============증\t\t정===============");
        for (OrderItemDto promoItemDto : orderResponseDto.getPromotionOrderItemDtos()) {
            System.out.printf("%-15s %-5d%n",
                    promoItemDto.getName(),
                    promoItemDto.getQuantity());
        }

        System.out.println("====================================");

        System.out.printf("%-15s %-5d %10s%n",
                "총구매액",
                orderResponseDto.getTotalOrderItemDtos().size(),
                String.format("%,d", orderResponseDto.getTotalPrice()));

        System.out.printf("%-15s %-5s %10s%n",
                "행사할인",
                "",
                "-" + String.format("%,d", orderResponseDto.getPromotionDiscountPrice()));

        System.out.printf("%-15s %-5s %10s%n",
                "멤버십할인",
                "",
                "-" + String.format("%,d", orderResponseDto.getMemberShipDiscountPrice()));

        System.out.printf("%-15s %-5s %10s%n",
                "내실돈",
                "",
                String.format("%,d", orderResponseDto.getFinalPrice()));
    }


}
