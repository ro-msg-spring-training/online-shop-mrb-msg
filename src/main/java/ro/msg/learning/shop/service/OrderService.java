package ro.msg.learning.shop.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.OrderDto;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.model.Address;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;


    public Order createOrder(OrderDto orderDto) {

        List<StockDto> availableStocks = findLocationWithStock(orderDto);

        if (availableStocks.isEmpty()) {
            //TODO add exception
        }

        updateStock();

        Order order = Order.builder()
                .createdOn(orderDto.getCreatedOn())
                .deliveryAddress(Address.builder()
                        .country(orderDto.getCountry())
                        .city(orderDto.getCity())
                        .details(orderDto.getDetails())
                        .build())
                .build();

        orderRepository.save(order);

        return order;


    }

    private List<StockDto> findLocationWithStock(OrderDto orderDto) {

        Map<UUID, List<StockDto>> map = new HashMap<>();

        orderDto.getProducts()
                .stream()
                .forEach(p -> {

                    List<Stock> stocks = stockRepository.findByProductAndQuantity(productRepository.findById(p.getProductId()).get(), p.getQuantity());

                    if (stocks.isEmpty()) {
                        //TODO throw exception
                    }

                    stocks.forEach(stock -> {
                        List<StockDto> list = map.get(stock.getLocation().getId());
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        list.add(new StockDto(stock.getProduct(), stock.getLocation(), stock.getQuantity()));
                        map.put(stock.getLocation().getId(), list);

                    });
                });

        for (Map.Entry<UUID, List<StockDto>> entry : map.entrySet()) {
            if (entry.getValue().size() == orderDto.getProducts().size()) {
                return entry.getValue();
            }

        }

        return new ArrayList<>();

    }

    private void updateStock() {

    }
}


