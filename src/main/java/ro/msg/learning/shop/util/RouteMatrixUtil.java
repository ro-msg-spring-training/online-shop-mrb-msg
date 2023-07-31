package ro.msg.learning.shop.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ro.msg.learning.shop.dto.LocationRequestDto;
import ro.msg.learning.shop.dto.RouteMatrixDto;
import ro.msg.learning.shop.exception.RouteMatrixApiException;
import ro.msg.learning.shop.model.Address;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class RouteMatrixUtil {

    private final RestTemplate restTemplate;

    public List<BigDecimal> getDistancesFromLocations(Address deliveryAddress, List<Address> locations) {

        var addressList = Stream.concat(Stream.of(deliveryAddress), locations.stream()).toList();
        var request = LocationRequestDto.builder().locations(addressList).build();
        var response = restTemplate.postForObject(
                "https://www.mapquestapi.com/directions/v2/routematrix?key=tP4aX1WEEXj1J2ANFcE8thLrjNQjqBzt", request, RouteMatrixDto.class);
        if (response == null) {
            throw new RouteMatrixApiException();
        }
        return response.getDistance();
    }
}
