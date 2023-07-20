package ro.msg.learning.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Product;

@AllArgsConstructor
@Data
public class StockDto {

    private Product product;
    private Location location;
    private Integer quantity;
}

