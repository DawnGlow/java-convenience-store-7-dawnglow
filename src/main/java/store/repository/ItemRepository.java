package store.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import store.domain.Item;

public class ItemRepository {
    private List<Item> items = new ArrayList<>();

    public void save(Item item) {
        items.add(item);
    }

    public List<Item> findByName(String name) {
        return items.stream()
                .filter(item -> item.getName().equals(name))
                .collect(Collectors.toList());
    }

    public List<Item> findAll() {
        return items;
    }

}
