package ro.msg.learning.shop.strategy;

import lombok.AllArgsConstructor;
import ro.msg.learning.shop.dto.ProductQuantityDto;
import ro.msg.learning.shop.dto.StockDto;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
public class GreedyStrategy implements LocationStrategy {
    @Override
    public List<StockDto> findLocation(List<ProductQuantityDto> products, List<BigDecimal> distances) {
        return null;
    }
}
