package ro.msg.learning.shop.strategy;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.model.Order;
import java.util.List;

@Component
public interface LocationStrategy {

    List<StockDto> findLocation(Order order);

}