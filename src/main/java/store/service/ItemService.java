package store.service;

import java.util.List;
import store.domain.Item;
import store.dto.OrderItemDto;
import store.repository.ItemRepository;

public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<String> findAllInformation() {
        return itemRepository.findAll().stream()
                .map(Item::toString)
                .toList();
    }

    public void save(Item item) {
        itemRepository.save(item);
    }

    public boolean isSufficientStock(OrderItemDto orderItemDto) {
        return itemRepository.findByName(orderItemDto.getName()).getFirst()
                .canPurchase(orderItemDto.getQuantity());
    }

    public int findStockByName(OrderItemDto orderItemDto) {
        return itemRepository.findByName(orderItemDto.getName()).getFirst().getStock();
    }

    public List<String> findItemNames() {
        return itemRepository.findAll().stream()
                .map(Item::getName)
                .toList();
    }


}
