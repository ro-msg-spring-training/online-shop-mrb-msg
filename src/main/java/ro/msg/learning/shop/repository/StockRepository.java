package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Stock;

import java.util.List;
import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {

    @Query(value = "SELECT s FROM Stock s WHERE s.product.id = :uuid AND s.quantity >= :quantity ORDER BY s.quantity DESC")
    List<Stock> findByProductIdAndQuantity(@Param("uuid") UUID uuid, @Param("quantity") Integer quantity);

    Stock findByProductAndLocation(Product product, Location location);

    @Query(value = "SELECT s FROM Stock s WHERE s.product.id = :uuid AND s.quantity >= :quantity ORDER BY s.quantity DESC LIMIT 1")
    Stock findByProductIdAndQuantityMostAbundant(@Param("uuid") UUID uuid, @Param("quantity") Integer quantity);

}
