package ro.msg.learning.shop.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ro.msg.learning.shop.exception.NoStocksAvailableException;
import ro.msg.learning.shop.exception.ResourceNotFoundException;
import ro.msg.learning.shop.exception.StrategyNotImplementedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoStocksAvailableException.class)
    public ResponseEntity<String> handleNoStocksAvailableException(NoStocksAvailableException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(StrategyNotImplementedException.class)
    public ResponseEntity<String> handleStrategyNotImplementedException(StrategyNotImplementedException ex) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(ex.getMessage());
    }


}