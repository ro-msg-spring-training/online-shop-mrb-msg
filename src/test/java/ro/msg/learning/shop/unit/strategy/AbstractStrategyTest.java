package ro.msg.learning.shop.unit.strategy;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import ro.msg.learning.shop.dto.ProductQuantityDto;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Stock;

import java.util.List;
import java.util.UUID;

public abstract class AbstractStrategyTest {

    protected List<ProductQuantityDto> orderedProducts;
    protected List<Stock> resultingStocks;


    @Before
    public void setUp() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        Product product1 = Product.builder().build();
        Product product2 = Product.builder().build();

        orderedProducts = List.of(
                ProductQuantityDto.builder().productId(product1.getId()).quantity(10).build(),
                ProductQuantityDto.builder().productId(product2.getId()).quantity(10).build()
        );

        Location location = Location.builder().name("TM").build();

        resultingStocks = List.of(
                Stock.builder().product(product1).quantity(10).location(location).build(),
                Stock.builder().product(product2).quantity(10).location(location).build());

    }
}
