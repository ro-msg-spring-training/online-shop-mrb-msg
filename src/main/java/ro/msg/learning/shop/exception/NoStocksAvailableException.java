package ro.msg.learning.shop.exception;

public class NoStocksAvailableException extends RuntimeException {

    public NoStocksAvailableException(String message) {
        super(message);
    }
}
