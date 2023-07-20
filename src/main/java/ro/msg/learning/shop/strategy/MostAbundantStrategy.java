package ro.msg.learning.shop.strategy;

import ro.msg.learning.shop.dto.ProductQuantityDto;
import ro.msg.learning.shop.dto.StockDto;

import java.util.List;

public class MostAbundantStrategy implements LocationStrategy {
    @Override
    public List<StockDto> findLocation(List<ProductQuantityDto> products) {
        return null;
    }
}
