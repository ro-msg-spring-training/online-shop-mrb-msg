package ro.msg.learning.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.model.Address;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RouteMatrixDto {

    private List<Address> locations;
    private List<BigDecimal> distance;
    private Boolean allToAll;
    private Boolean manyToOne;

    private Info info;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Info {
            @JsonProperty("statusCode")
        private Integer statusCode;
        private List<String> messages;
    }
}
