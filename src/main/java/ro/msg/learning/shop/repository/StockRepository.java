package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Stock;

import java.util.List;
import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {

    @Query(value = "SELECT s FROM Stock s WHERE s.product = :product AND s.quantity >= :quantity ORDER BY s.quantity DESC")
    List<Stock> findByProductAndQuantity(Product product, Integer quantity);
}
