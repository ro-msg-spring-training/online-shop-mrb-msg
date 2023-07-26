package ro.msg.learning.shop.service.TestService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.BaseEntity;
import ro.msg.learning.shop.repository.CategoryRepository;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.OrderDetailsRepository;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.repository.SupplierRepository;

import static ro.msg.learning.shop.util.TestDataUtil.*;

@Service
@RequiredArgsConstructor
@Profile("test")
public class TestService {

    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final CustomerRepository customerRepository;
    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;


    public void populateData() {
        saveAndUpdateEntityId(food, categoryRepository);
        saveAndUpdateEntityId(drinks, categoryRepository);
        saveAndUpdateEntityId(supplier, supplierRepository);
        saveAndUpdateEntityId(Tm, locationRepository);
        saveAndUpdateEntityId(Ar, locationRepository);
        saveAndUpdateEntityId(bread, productRepository);
        saveAndUpdateEntityId(cola, productRepository);
        stockRepository.saveAll(stockList);

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

    public static <T extends BaseEntity, UUID> void saveAndUpdateEntityId(T entity, JpaRepository<T, UUID> repository) {
        var newEntity = repository.save(entity);
        entity.setId(newEntity.getId());
    }
}
