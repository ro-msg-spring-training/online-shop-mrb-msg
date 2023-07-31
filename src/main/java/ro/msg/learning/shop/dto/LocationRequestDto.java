package ro.msg.learning.shop.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import ro.msg.learning.shop.model.Address;

import java.util.List;

@AllArgsConstructor
@Builder
@Jacksonized
public class LocationRequestDto {

    @JsonUnwrapped
    private List<Address> locations;
}
