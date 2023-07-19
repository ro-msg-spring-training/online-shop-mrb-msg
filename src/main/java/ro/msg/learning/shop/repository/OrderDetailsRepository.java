package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.model.OrderDetail;

import java.util.UUID;
import java.util.jar.JarEntry;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail, UUID> {
}
