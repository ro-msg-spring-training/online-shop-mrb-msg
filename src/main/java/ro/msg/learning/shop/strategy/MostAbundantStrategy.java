package ro.msg.learning.shop.strategy;

import lombok.AllArgsConstructor;
import ro.msg.learning.shop.dto.ProductQuantityDto;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.exception.NoStocksAvailableException;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.util.StockMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class MostAbundantStrategy implements LocationStrategy {

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final StockMapper stockMapper;

    @Override
    public List<StockDto> findLocation(List<ProductQuantityDto> products) {

        Map<UUID, StockDto> map = new HashMap<>();

        products.forEach(p -> {

            List<Stock> availableStocks = stockRepository.findByProductAndQuantity(
                    productRepository.findById(p.getProductId()).get(), p.getQuantity());

//            if (availableStocks.isEmpty() || availableStocks.size() < products.size()) {
//                throw new NoStocksAvailableException();
//            }

            availableStocks.forEach(s -> {

                StockDto stockDto = map.get(p.getProductId()); //pentru UUID am luat value din map, adica stock

                if (!map.containsKey(p.getProductId()))
                    map.put(p.getProductId(), stockMapper.toDto(s));
                else if (stockDto.getQuantity() < s.getQuantity()) {
                    map.put(p.getProductId(), stockMapper.toDto(s));
                }
            });
        });

        return new ArrayList<>(map.values());
    }
}

