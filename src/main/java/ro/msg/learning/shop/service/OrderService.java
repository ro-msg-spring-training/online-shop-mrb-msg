package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.dto.ProductQuantityDto;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.exception.NoStocksAvailableException;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.strategy.LocationStrategy;
import ro.msg.learning.shop.strategy.MostAbundantStrategy;
import ro.msg.learning.shop.strategy.SingleLocationStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final LocationStrategy locationStrategy;

    public OrderService(@Value("${strategy.type}") String strategyType, OrderRepository orderRepository, ProductRepository productRepository, StockRepository stockRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
        switch (strategyType) {
            case "SINGLE" -> locationStrategy = new SingleLocationStrategy(stockRepository, productRepository);
            case "ABUNDANT" -> locationStrategy = new MostAbundantStrategy(stockRepository, productRepository);
            default -> throw new NoStocksAvailableException();
        }
    }

    @Transactional
    public Order createOrder(Order order, List<ProductQuantityDto> products) {

//        List<StockDto> availableStocks = findLocationWithStock(products);

        List<StockDto> availableStocks = locationStrategy.findLocation(products);

        if (availableStocks.isEmpty()) {
            throw new NoStocksAvailableException();
        }

        Set<OrderDetail> orderDetails = products.stream()
                .map(p -> new OrderDetail(productRepository.findById(p.getProductId()).get(), p.getQuantity()))
                .collect(Collectors.toSet());

        Order toBeSaved = new Order();
        toBeSaved.setCreatedOn(order.getCreatedOn());
        toBeSaved.setOrderDetails(orderDetails);
        toBeSaved.setDeliveryAddress(order.getDeliveryAddress());

        products.forEach(p -> {
            availableStocks.forEach(s -> {
                if (p.getProductId().equals(s.getProduct().getId())) {
                    updateStock(s, p.getQuantity());
                }
            });
        });

        return orderRepository.save(toBeSaved);

    }

    private List<StockDto> findLocationWithStock(List<ProductQuantityDto> products) {

        Map<UUID, List<StockDto>> map = new HashMap<>();

        products.forEach(p -> {

            List<Stock> stocks = stockRepository.findByProductAndQuantity(productRepository.findById(p.getProductId()).get(), p.getQuantity());

            if (stocks.isEmpty()) {
                throw new NoStocksAvailableException();
            }

            stocks.forEach(stock -> {
                List<StockDto> list = map.get(stock.getLocation().getId());
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(new StockDto(stock.getProduct(), stock.getLocation(), p.getQuantity()));
                map.put(stock.getLocation().getId(), list);

            });
        });

        for (Map.Entry<UUID, List<StockDto>> entry : map.entrySet()) {
            if (entry.getValue().size() == products.size()) {
                return entry.getValue();
            }

        }

        return new ArrayList<>();

    }

    private void updateStock(StockDto stockDto, Integer quantity) {

        Stock stockToUpdate = stockRepository.findByProductAndLocation(stockDto.getProduct(), stockDto.getLocation());

        Integer quantityToUpdate = stockToUpdate.getQuantity() - quantity;
        stockToUpdate.setQuantity(quantityToUpdate);

        stockRepository.save(stockToUpdate);
    }
}


