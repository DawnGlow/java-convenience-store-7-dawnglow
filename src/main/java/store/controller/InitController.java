package store.controller;

import java.util.ArrayList;
import java.util.List;
import store.domain.Item;
import store.domain.PromotionInfo;
import store.domain.PromotionItem;
import store.domain.builder.PromotionItemBuilder;
import store.dto.ItemDto;
import store.repository.ItemRepository;
import store.repository.PromotionItemRepository;
import store.repository.PromotionRepository;
import store.utils.ItemDataReader;
import store.utils.PromotionDataReader;

public class InitController {
    private final ItemRepository itemRepository;
    private final PromotionItemRepository promotionItemRepository;
    private final PromotionRepository promotionRepository;

    public InitController(ItemRepository itemRepository, PromotionItemRepository promotionItemRepository,
                          PromotionRepository promotionRepository) {
        this.itemRepository = itemRepository;
        this.promotionItemRepository = promotionItemRepository;
        this.promotionRepository = promotionRepository;
    }

    public void initData() {
        try {
            loadAndSavePromotions();
            List<ItemDto> itemDtos = loadItemDtos();
            List<Item> nonPromotionItems = findNonPromotionItems(itemDtos);
            List<PromotionItem> promotionItems = findPromotionItems(itemDtos);
            saveItems(nonPromotionItems, promotionItems);
            List<Item> missingGeneralItems = findMissingGeneralItems(itemDtos);
            saveMissingGeneralItems(missingGeneralItems);
        } catch (Exception e) {
            handleInitializationError(e);
        }
    }

    private void loadAndSavePromotions() {
        PromotionDataReader.readData().forEach(promotionRepository::save);
    }

    private List<ItemDto> loadItemDtos() {
        return ItemDataReader.readData();
    }

    public List<Item> findNonPromotionItems(List<ItemDto> itemDtos) {
        List<Item> items = new ArrayList<>();
        itemDtos.forEach(itemDto -> {
            if (!itemDto.isPromotion()) {
                items.add(new Item(itemDto.getName(), itemDto.getPrice(), itemDto.getStock()));
            }
        });
        return items;
    }

    public List<PromotionItem> findPromotionItems(List<ItemDto> itemDtos) {
        List<PromotionItem> promotionItems = new ArrayList<>();
        itemDtos.forEach(itemDto -> {
            if (itemDto.isPromotion()) {
                PromotionInfo promotionInfo = promotionRepository.findPromotionInfoByTitle(itemDto.getPromotionTitle());
                validatePromotionInfo(promotionInfo, itemDto);
                promotionItems.add(buildPromotionItem(itemDto, promotionInfo));
            }
        });
        return promotionItems;
    }

    private void validatePromotionInfo(PromotionInfo promotionInfo, ItemDto itemDto) {
        if (promotionInfo == null) {
            throw new IllegalArgumentException("[ERROR] 프로모션 정보를 찾을 수 없습니다: " + itemDto.getPromotionTitle());
        }
    }

    private PromotionItem buildPromotionItem(ItemDto itemDto, PromotionInfo promotionInfo) {
        return new PromotionItemBuilder()
                .name(itemDto.getName())
                .price(itemDto.getPrice())
                .stock(itemDto.getStock())
                .promotionInfo(promotionInfo)
                .build();
    }

    private void saveItems(List<Item> nonPromotionItems, List<PromotionItem> promotionItems) {
        nonPromotionItems.forEach(itemRepository::save);
        promotionItems.forEach(promotionItemRepository::save);
    }

    private List<Item> findMissingGeneralItems(List<ItemDto> itemDtos) {
        List<Item> additionalItems = new ArrayList<>();
        itemDtos.forEach(itemDto -> {
            String itemName = itemDto.getName();
            if (isGeneralItemMissing(itemName)) {
                additionalItems.add(createMissingGeneralItem(itemDto));
            }
        });
        return additionalItems;
    }

    private boolean isGeneralItemMissing(String itemName) {
        return itemRepository.findByName(itemName).isEmpty();
    }

    private Item createMissingGeneralItem(ItemDto itemDto) {
        return new Item(itemDto.getName(), itemDto.getPrice(), 0);
    }

    private void saveMissingGeneralItems(List<Item> missingGeneralItems) {
        missingGeneralItems.forEach(itemRepository::save);
    }

    private void handleInitializationError(Exception e) {
        System.err.println("[ERROR] 데이터 초기화 중 오류 발생: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException(e);
    }
}
