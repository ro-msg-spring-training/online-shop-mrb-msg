package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.dto.ProductQuantityDto;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.exception.NoStocksAvailableException;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.strategy.LocationStrategy;
import ro.msg.learning.shop.util.OrderMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;
    private final LocationStrategy locationStrategy;
    private final OrderMapper orderMapper;


    @Transactional
    public Order createOrder(Order order) {

        List<ProductQuantityDto> products = orderMapper.mapOrderDetailsToProductQuantityDto(order.getOrderDetails());
        List<StockDto> stocksToBeOrdered = locationStrategy.findLocation(order);

        if (stocksToBeOrdered.isEmpty()) {
            throw new NoStocksAvailableException("No stocks available");
        }

        Set<OrderDetail> orderDetails = new HashSet<>();

        stocksToBeOrdered.forEach(stock -> products.forEach(product -> {
            if (stock.getProduct().getId().equals(product.getProductId())) {
                orderDetails.add(new OrderDetail(stock.getProduct(), product.getQuantity(), stock.getLocation()));
            }
        }));

        Order toBeSaved = new Order();
        toBeSaved.setCreatedOn(order.getCreatedOn());
        toBeSaved.setOrderDetails(orderDetails);
        toBeSaved.setDeliveryAddress(order.getDeliveryAddress());

        products.forEach(p -> stocksToBeOrdered.forEach(s -> {
            if (p.getProductId().equals(s.getProduct().getId())) {
                updateStock(s, p.getQuantity());
            }
        }));

        return orderRepository.save(toBeSaved);

    }

    private void updateStock(StockDto stockDto, Integer quantity) {

        Stock stockToUpdate = stockRepository.findByProductAndLocation(stockDto.getProduct(), stockDto.getLocation());

        Integer quantityToUpdate = stockToUpdate.getQuantity() - quantity;
        stockToUpdate.setQuantity(quantityToUpdate);

        stockRepository.save(stockToUpdate);
    }
}


