package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.Revenue;
import ro.msg.learning.shop.model.RevenueForLocation;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, UUID> {

    List<Revenue> findAllByDate(LocalDate date);

    @Query(value = "SELECT od.location_id AS locationId, SUM(od.quantity * p.price) as sum " +
            "FROM Orders o JOIN Order_Details od ON o.id = od.order_id " +
            "JOIN Products p ON p.id = od.product_id " +
            "WHERE o.created_on = :date " +
            "GROUP BY od.location_id, o.created_on", nativeQuery = true)
    List<RevenueForLocation> findAllRevenuesPerLocationForDate(@Param("date") LocalDate date);




}