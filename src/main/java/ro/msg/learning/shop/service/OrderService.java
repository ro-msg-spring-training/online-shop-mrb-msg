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
import ro.msg.learning.shop.util.StockMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;
    private final LocationStrategy locationStrategy;

    public OrderService(@Value("${strategy.type}") String strategyType, OrderRepository orderRepository, ProductRepository productRepository, StockRepository stockRepository, StockMapper stockMapper) {
        this.orderRepository = orderRepository;
        this.stockRepository = stockRepository;
        switch (strategyType) {
            case "SINGLE" -> locationStrategy = new SingleLocationStrategy(stockRepository, productRepository);
            case "ABUNDANT" ->
                    locationStrategy = new MostAbundantStrategy(stockRepository, productRepository, stockMapper);
            default -> throw new NoStocksAvailableException();
        }
    }

    @Transactional
    public Order createOrder(Order order, List<ProductQuantityDto> products) {

        List<StockDto> stocksToBeOrdered = locationStrategy.findLocation(products);

        if (stocksToBeOrdered.isEmpty()) {
            throw new NoStocksAvailableException();
        }

        Set<OrderDetail> orderDetails = new HashSet<>();

        stocksToBeOrdered.forEach(stock -> {
            products.forEach(product -> {
                if (stock.getProduct().getId().equals(product.getProductId())) {
                    orderDetails.add(new OrderDetail(stock.getProduct(), product.getQuantity()));
                }
            });
        });

        Order toBeSaved = new Order();
        toBeSaved.setCreatedOn(order.getCreatedOn());
        toBeSaved.setOrderDetails(orderDetails);
        toBeSaved.setDeliveryAddress(order.getDeliveryAddress());

        products.forEach(p -> {
            stocksToBeOrdered.forEach(s -> {
                if (p.getProductId().equals(s.getProduct().getId())) {
                    updateStock(s, p.getQuantity());
                }
            });
        });

        return orderRepository.save(toBeSaved);

    }

    private void updateStock(StockDto stockDto, Integer quantity) {

        Stock stockToUpdate = stockRepository.findByProductAndLocation(stockDto.getProduct(), stockDto.getLocation());

        Integer quantityToUpdate = stockToUpdate.getQuantity() - quantity;
        stockToUpdate.setQuantity(quantityToUpdate);

        stockRepository.save(stockToUpdate);
    }
}


