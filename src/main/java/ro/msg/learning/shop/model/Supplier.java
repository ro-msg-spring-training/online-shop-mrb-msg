package ro.msg.learning.shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Suppliers")
public class Supplier extends BaseEntity {

    private String name;
}
