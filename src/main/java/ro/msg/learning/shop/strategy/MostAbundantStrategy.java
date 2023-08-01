package ro.msg.learning.shop.strategy;

import lombok.AllArgsConstructor;
import ro.msg.learning.shop.dto.ProductQuantityDto;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.util.OrderMapper;
import ro.msg.learning.shop.util.StockMapper;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class MostAbundantStrategy implements LocationStrategy {

    private final StockRepository stockRepository;
    private final StockMapper stockMapper;
    private final OrderMapper orderMapper;

    @Override
    public List<StockDto> findLocation(Order order) {

        List<ProductQuantityDto> products = orderMapper.mapOrderDetailsToProductQuantityDto(order.getOrderDetails());
        List<StockDto> mostAbundantStocks = new ArrayList<>();

        products.forEach(p -> {

            Stock stock = stockRepository.findByProductIdAndQuantityMostAbundant(p.getProductId(), p.getQuantity());
            mostAbundantStocks.add(stockMapper.toDto(stock));

        });

        return mostAbundantStocks;

    }
}

