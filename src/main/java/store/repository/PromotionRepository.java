package store.repository;

import java.util.ArrayList;
import java.util.List;
import store.domain.PromotionInfo;

public class PromotionRepository {
    private final List<PromotionInfo> promotionInfos = new ArrayList<>();

    public void save(PromotionInfo promotionInfo) {
        promotionInfos.add(promotionInfo);
    }

    public PromotionInfo findByName(String name) {
        for (PromotionInfo promotionInfo : promotionInfos) {
            if (promotionInfo.getTitle().equals(name)) {
                return promotionInfo;
            }
        }
        return null;
    }

    public List<PromotionInfo> findAll() {
        return promotionInfos;
    }

    public PromotionInfo findPromotionInfoByTitle(String promotionTitle) {
        return promotionInfos.stream()
                .filter(promotionInfo -> promotionInfo.getTitle().equals(promotionTitle))
                .findFirst()
                .orElse(null);
    }
}
