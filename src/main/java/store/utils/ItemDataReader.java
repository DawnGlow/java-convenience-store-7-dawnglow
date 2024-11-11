package store.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import store.dto.ItemDto;

public class ItemDataReader {
    private static final String PRODUCT_DATA_PATH = "src/main/resources/products.md";
    
    public static List<ItemDto> readData() {
        List<String> itemData;
        try {
            itemData = Files.readAllLines(Paths.get(PRODUCT_DATA_PATH));
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 파일을 읽을 수 없습니다.");
        }

        return itemData.stream()
                .skip(1) // 첫 줄(헤더) 무시
                .filter(line -> !line.trim().isEmpty()) // 빈 줄 무시
                .map(ItemDataReader::parseItem)
                .collect(Collectors.toList());
    }

    private static ItemDto parseItem(String itemData) {
        String[] itemInfo = itemData.split(",");
        if (itemInfo.length < 4) {
            throw new IllegalArgumentException("[ERROR] 잘못된 데이터 형식: " + itemData);
        }

        String name = itemInfo[0].trim();
        int price;
        int stock;
        try {
            price = Integer.parseInt(itemInfo[1].trim());
            stock = Integer.parseInt(itemInfo[2].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 가격 또는 재고가 유효하지 않습니다: " + itemData);
        }

        String promotionTitle = itemInfo[3].trim();
        if (promotionTitle.equalsIgnoreCase("null")) {
            promotionTitle = null;
        }

        ItemDto itemDto = new ItemDto(name, price, stock);
        itemDto.setPromotionTitle(promotionTitle);
        return itemDto;
    }
}
