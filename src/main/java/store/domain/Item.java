package store.domain;

public class Item {
    protected final String name;

    protected final int price;

    protected int stock;

    public Item(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public int getPrice() {
        return price;
    }

    public boolean canPurchase(int quantity) {
        return stock >= quantity;
    }

    public int getPriceByQuantity(int quantity) {
        return price * quantity;
    }

    public void decreaseStock(int quantity) {
        this.stock -= quantity;
    }

    @Override
    public String toString() {
        String stockInfo = (stock > 0) ? stock + "개" : "재고 없음";
        return "- " + name + " " + formatPrice(price) + " " + stockInfo;
    }

    protected String formatPrice(int price) {
        return String.format("%,d원", price);
    }
}
