package ro.msg.learning.shop.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Table(name = "Locations")
public class Location extends BaseEntity {

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "location")
    private Set<Stock> stocks;
}
