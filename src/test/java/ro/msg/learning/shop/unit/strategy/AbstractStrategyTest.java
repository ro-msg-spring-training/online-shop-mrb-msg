package ro.msg.learning.shop.unit.strategy;

import org.junit.Before;
import ro.msg.learning.shop.dto.ProductQuantityDto;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Stock;

import java.util.List;
import java.util.UUID;

public abstract class AbstractStrategyTest {

    protected List<ProductQuantityDto> orderedProducts;

    protected  Stock resultingStock1;
    protected Stock resultingStock2;
    protected List<Stock> resultingStockList1;
    protected List<Stock> resultingStockList2;
    protected List<StockDto> expectedResult;


    @Before
    public void setUp() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        Product product1 = Product.builder().name("Bread").price(10.0).weight(100.0).build();
        Product product2 = Product.builder().name("Cola").price(7.0).weight(250.0).build();

        orderedProducts = List.of(
                ProductQuantityDto.builder().productId(product1.getId()).quantity(10).build(),
                ProductQuantityDto.builder().productId(product2.getId()).quantity(10).build()
        );

        Location location = Location.builder().name("TM").build();

        resultingStock1 = Stock.builder().product(product1).quantity(10).location(location).build();
        resultingStockList1 = List.of(resultingStock1);

        resultingStock2 = Stock.builder().product(product2).quantity(10).location(location).build();
        resultingStockList2 = List.of(resultingStock2);

        expectedResult = List.of(
                StockDto.builder().product(product1).location(location).quantity(10).build(),
                StockDto.builder().product(product2).location(location).quantity(10).build());
    }
}
