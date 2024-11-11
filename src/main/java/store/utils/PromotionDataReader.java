package store.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import store.domain.PromotionInfo;
import store.domain.builder.PromotionInfoBuilder;

public class PromotionDataReader {
    private static final String PROMOTION_DATA_PATH = "src/main/resources/promotions.md";

    public static List<PromotionInfo> readData() {
        List<String> promotionData;
        try {
            promotionData = Files.readAllLines(Paths.get(PROMOTION_DATA_PATH));
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 파일을 읽을 수 없습니다.");
        }
        return promotionData.stream()
                .skip(1) // 첫 줄(헤더) 무시
                .filter(line -> !line.trim().isEmpty()) // 빈 줄 무시
                .map(PromotionDataReader::parsePromotion)
                .collect(Collectors.toList());
    }

    private static PromotionInfo parsePromotion(String promotionData) {
        String[] promotionInfo = promotionData.split(",");
        if (promotionInfo.length < 5) {
            throw new IllegalArgumentException("[ERROR] 잘못된 데이터 형식: " + promotionData);
        }

        String title = promotionInfo[0].trim();
        int buy;
        int get;
        try {
            buy = Integer.parseInt(promotionInfo[1].trim());
            get = Integer.parseInt(promotionInfo[2].trim());
            buy += get;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] buy 또는 get 값이 유효하지 않습니다: " + promotionData);
        }

        LocalDateTime startDate;
        LocalDateTime endDate;
        try {
            startDate = LocalDateTime.parse(promotionInfo[3].trim() + "T00:00:00");
            endDate = LocalDateTime.parse(promotionInfo[4].trim() + "T23:59:59");
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 날짜 형식이 유효하지 않습니다: " + promotionData);
        }

        return new PromotionInfoBuilder()
                .title(title)
                .requiredQuantity(buy)
                .presentQuantity(get)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
