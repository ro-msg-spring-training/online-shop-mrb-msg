package ro.msg.learning.shop.strategy;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.CreateOrderDto;
import ro.msg.learning.shop.dto.ProductQuantityDto;
import ro.msg.learning.shop.dto.StockDto;

import java.util.List;

@Component
public interface LocationStrategy {

        List<StockDto> findLocation(List<ProductQuantityDto> products);

}