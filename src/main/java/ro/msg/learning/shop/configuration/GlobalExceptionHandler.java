package ro.msg.learning.shop.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ro.msg.learning.shop.exception.NoStocksAvailableException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoStocksAvailableException.class)
    public ResponseEntity<String> handleNoStocksAvailableException(NoStocksAvailableException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No stocks available for the requested products.");
    }


}