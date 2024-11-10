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

    public boolean canPurchase(int amount) {
        return stock >= amount;
    }

    public int getPriceByAmount(int amount) {
        return price * amount;
    }

    public void decreaseStock(int amount) {
        this.stock -= amount;
    }

    @Override
    public String toString() {
        return "-" + name + " " + price + "원 " + stock + "개";
    }
}
