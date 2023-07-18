package ro.msg.learning.shop.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
