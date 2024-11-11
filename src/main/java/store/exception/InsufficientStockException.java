package store.exception;

public class InsufficientStockException extends IllegalArgumentException {
    public InsufficientStockException(String message) {
        super(message);
    }
}
