package ro.msg.learning.shop.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Orders")
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Order extends BaseEntity {

    @Column(nullable = false)
    private LocalDate createdOn;

    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<Product> products;

    @Embedded
    private Address deliveryAddress;


}
