package ro.msg.learning.shop.dto;

import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto extends BaseDto {

    private String name;
    private String description;
    private Double price;
    private Double weight;
    private UUID categoryId;
    private UUID supplierId;
}
