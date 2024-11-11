package store.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.dto.OrderItemDto;

public class OrderParser {

    /**
     * 주문 입력 문자열을 파싱하여 OrderItemDto 리스트로 반환하는 메서드
     *
     * @param order 주문 입력 문자열 (예: "[사이다-2],[감자칩-1]")
     * @return List<OrderItemDto>
     */
    public static List<OrderItemDto> parseToOrderItemDtos(String order) {
        List<String> subOrders = extractSubOrders(order);
        return subOrders.stream()
                .map(OrderParser::parseToOrderItemDto)
                .toList();
    }

    /**
     * 주문 입력 문자열에서 각 상품 주문을 추출하는 메서드
     *
     * @param order 주문 입력 문자열
     * @return List<String> 각 상품 주문 문자열
     */
    private static List<String> extractSubOrders(String order) {
        // 정규식을 사용하여 대괄호 안의 내용을 추출
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(order);
        List<String> subOrders = new ArrayList<>();

        while (matcher.find()) {
            subOrders.add(matcher.group(1));
        }

        if (subOrders.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 주문 형식이 잘못되었습니다: " + order);
        }

        return subOrders;
    }

    /**
     * 각 상품 주문 문자열을 파싱하여 OrderItemDto 객체로 변환하는 메서드
     *
     * @param subOrder 각 상품 주문 문자열 (예: "사이다-2")
     * @return OrderItemDto
     */
    private static OrderItemDto parseToOrderItemDto(String subOrder) {
        String[] item = subOrder.split("-");
        if (item.length != 2) {
            throw new IllegalArgumentException("[ERROR] 잘못된 주문 항목 형식: " + subOrder);
        }

        String name = item[0].trim();
        int quantity;

        try {
            quantity = Integer.parseInt(item[1].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 수량이 유효하지 않습니다: " + subOrder);
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("[ERROR] 수량은 1 이상이어야 합니다: " + subOrder);
        }

        return new OrderItemDto(name, quantity);
    }
}
