package store.domain.builder;

import store.domain.Item;

public class ItemBuilder {
    private String name;
    private int price;
    private int stock;

    public ItemBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder price(int price) {
        this.price = price;
        return this;
    }

    public ItemBuilder stock(int stock) {
        this.stock = stock;
        return this;
    }

    public Item build() {
        return new Item(name, price, stock);
    }
}
