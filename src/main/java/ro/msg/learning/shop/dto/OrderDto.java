package ro.msg.learning.shop.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto extends BaseDto {

    private LocalDate createdOn;
    private String country;
    private String city;
    private String details;
    private List<ProductQuantityDto> products;
}
