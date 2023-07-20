package ro.msg.learning.shop.strategy;

import lombok.AllArgsConstructor;
import ro.msg.learning.shop.dto.ProductQuantityDto;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.List;

@AllArgsConstructor
public class MostAbundantStrategy implements LocationStrategy {

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;

    @Override
    public List<StockDto> findLocation(List<ProductQuantityDto> products) {
        return null;
    }
}
