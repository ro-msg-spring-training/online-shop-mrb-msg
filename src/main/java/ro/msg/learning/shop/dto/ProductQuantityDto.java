package ro.msg.learning.shop.dto;

import lombok.*;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductQuantityDto extends BaseDto {

    private UUID productId;
    private Integer quantity;
}
