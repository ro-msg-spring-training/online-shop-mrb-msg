package ro.msg.learning.shop.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.model.Stock;

@Component
@RequiredArgsConstructor
public class StockMapper {

    public StockDto toDto(Stock stock) {
        return StockDto.builder()
                .product(stock.getProduct())
                .location(stock.getLocation())
                .quantity(stock.getQuantity())
                .build();
    }
}
