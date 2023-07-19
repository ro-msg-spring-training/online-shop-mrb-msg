package ro.msg.learning.shop.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.CreateOrderDto;
import ro.msg.learning.shop.dto.OrderDto;
import ro.msg.learning.shop.model.Address;
import ro.msg.learning.shop.model.Order;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    public Order toEntity(OrderDto createOrderDto) {
        return Order.builder()
                .createdOn(createOrderDto.getCreatedOn())
                .customer(createOrderDto.getCustomer())
                .orderDetails(createOrderDto.getOrderDetails())
                .deliveryAddress(new Address(createOrderDto.getCountry(), createOrderDto.getCity(), createOrderDto.getDetails()))
                .build();
    }

    public Order toEntity(CreateOrderDto createOrderDto) {
        return Order.builder()
                .createdOn(createOrderDto.getCreatedOn())
                .deliveryAddress(Address.builder()
                        .country(createOrderDto.getCountry())
                        .city(createOrderDto.getCity())
                        .details(createOrderDto.getDetails())
                        .build())
                .build();
    }

    public OrderDto toDto(Order order) {
        return OrderDto.builder()
                .createdOn(order.getCreatedOn())
                .customer(order.getCustomer())
                .orderDetails(order.getOrderDetails())
                .country(order.getDeliveryAddress().getCountry())
                .city(order.getDeliveryAddress().getCity())
                .details(order.getDeliveryAddress().getDetails())
                .build();
    }

    public CreateOrderDto createOrderDto(Order order) {
        return CreateOrderDto.builder()
                .createdOn(order.getCreatedOn())
                .country(order.getDeliveryAddress().getCountry())
                .city(order.getDeliveryAddress().getCity())
                .details(order.getDeliveryAddress().getDetails())
                .build();
    }
}
