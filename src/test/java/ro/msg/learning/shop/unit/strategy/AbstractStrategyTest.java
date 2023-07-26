package ro.msg.learning.shop.unit.strategy;

import org.junit.Before;
import ro.msg.learning.shop.dto.ProductQuantityDto;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Stock;

import java.util.List;
import java.util.UUID;

public abstract class AbstractStrategyTest {

    protected List<ProductQuantityDto> orderedProducts;

    protected Stock stock1;
    protected Stock stock2;
    protected Stock stock3;
    protected Stock stock4;
    protected List<Stock> mostAbundantStocks;
    protected List<Stock> singleLocationStocks;

    @Before
    public void setUp() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        Product bread = Product.builder().name("Bread").price(10.0).weight(100.0).build();
        Product cola = Product.builder().name("Cola").price(7.0).weight(250.0).build();

        orderedProducts = List.of(
                ProductQuantityDto.builder().productId(bread.getId()).quantity(10).build(),
                ProductQuantityDto.builder().productId(cola.getId()).quantity(10).build()
        );

        Location tm = Location.builder().name("TM").build();
        Location ar = Location.builder().name("AR").build();


        stock1 = Stock.builder()
                .product(bread)
                .quantity(10)
                .location(ar)
                .build();
        stock2 = Stock.builder()
                .product(bread)
                .quantity(11)
                .location(tm)
                .build();
        stock3 = Stock.builder()
                .product(cola)
                .quantity(10)
                .location(ar)
                .build();
        stock4 = Stock.builder()
                .product(cola)
                .quantity(11)
                .location(tm)
                .build();

        mostAbundantStocks = List.of(stock2, stock4);
        singleLocationStocks = List.of(stock1, stock3);


    }
}
