package ro.msg.learning.shop.service.TestService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.Address;
import ro.msg.learning.shop.model.Category;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.model.Supplier;
import ro.msg.learning.shop.repository.CategoryRepository;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.OrderDetailsRepository;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.repository.SupplierRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Profile("test")
@Transactional
public class TestService {

    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final CustomerRepository customerRepository;
    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    public static final Category food = Category.builder().name("Food").description("food").build();
    public static final Category drinks = Category.builder().name("Drinks").description("drinks").build();
    public static final Supplier supplier = Supplier.builder().name("Auchan").build();
    public static final Location Tm = Location.builder().name("TM").address(Address.builder().build()).build();
    public static final Location Ar = Location.builder().name("AR").address(Address.builder().build()).build();
    public static final Product bread = Product.builder().name("Bread").description("Gluten free").weight(100.0).price(10.0).category(food).supplier(supplier).build();
    public static final Product cola = Product.builder().name("Cola").description("Cola Zero").weight(500.0).price(7.0).category(drinks).supplier(supplier).build();
    public static final Stock breadStockTm = Stock.builder().product(bread).location(Tm).quantity(100).build();
    public static final Stock colaStockTm = Stock.builder().product(cola).location(Ar).quantity(100).build();
    public static final Stock breadStockAr = Stock.builder().product(bread).location(Ar).quantity(100).build();
    public static final Stock colaStockAr = Stock.builder().product(cola).location(Ar).quantity(100).build();


    public void populateData() {
        stockRepository.saveAll(createEntities());
    }

    public void clearData() {
        orderDetailsRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();
        customerRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        locationRepository.deleteAllInBatch();
        supplierRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();
    }

    public List<Stock> createEntities() {
        List<Stock> stockList = List.of(breadStockTm, breadStockAr, colaStockTm, colaStockAr);
        return stockList;
    }
}
