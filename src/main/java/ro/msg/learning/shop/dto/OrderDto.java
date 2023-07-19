package ro.msg.learning.shop.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.model.Customer;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto extends BaseDto {

    private LocalDate createdOn;
    private Customer customer;
    private Set<OrderDetail> orderDetails;
    private String country;
    private String city;
    private String details;
}
