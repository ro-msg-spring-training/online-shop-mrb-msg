package ro.msg.learning.shop.util;

import lombok.experimental.UtilityClass;
import ro.msg.learning.shop.model.Address;
import ro.msg.learning.shop.model.Category;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.model.Supplier;

import java.util.List;

@UtilityClass
public class TestDataUtil {

    public static final Category food = Category.builder().name("Food").description("food").build();
    public static final Category drinks = Category.builder().name("Drinks").description("drinks").build();
    public static final Supplier supplier = Supplier.builder().name("Auchan").build();
    public static final Location Tm = Location.builder().name("TM").address(Address.builder().country("Romania").city("Timisoara").details("none").build()).build();
    public static final Location Ar = Location.builder().name("AR").address(Address.builder().country("Romania").city("Arad").details("none").build()).build();
    public static final Product bread = Product.builder().name("Bread").description("Gluten free").weight(100.0).price(10.0).category(food).supplier(supplier).build();
    public static final Product cola = Product.builder().name("Cola").description("Cola Zero").weight(500.0).price(7.0).category(drinks).supplier(supplier).build();
    public static final Stock breadStockTm = Stock.builder().product(bread).location(Tm).quantity(100).build();
    public static final Stock colaStockTm = Stock.builder().product(cola).location(Ar).quantity(100).build();
    public static final Stock breadStockAr = Stock.builder().product(bread).location(Ar).quantity(100).build();
    public static final Stock colaStockAr = Stock.builder().product(cola).location(Ar).quantity(100).build();

    public static final List<Stock> stockList = List.of(breadStockTm, breadStockAr, colaStockTm, colaStockAr);
}
