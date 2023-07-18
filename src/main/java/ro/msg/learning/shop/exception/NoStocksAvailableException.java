package ro.msg.learning.shop.exception;

public class NoStocksAvailableException extends RuntimeException {

    public NoStocksAvailableException() {
        super("No location with available stock found");
    }
}
