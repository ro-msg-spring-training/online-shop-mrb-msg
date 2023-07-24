package ro.msg.learning.shop.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.util.LocationSerializer;
import ro.msg.learning.shop.util.ProductSerializer;

@AllArgsConstructor
@Data
@Builder
public class StockDto {

    @JsonSerialize(using = ProductSerializer.class)
    private Product product;

    @JsonSerialize(using = LocationSerializer.class)
    private Location location;

    private Integer quantity;
}

