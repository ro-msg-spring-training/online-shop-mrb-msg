package ro.msg.learning.shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Categories")
public class Category extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

}
