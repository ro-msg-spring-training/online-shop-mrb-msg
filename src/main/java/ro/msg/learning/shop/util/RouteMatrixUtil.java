package ro.msg.learning.shop.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${location.api-host}")
    private String apiHost;

    @Value("${location.api-key}")
    private String apiKey;

    public List<BigDecimal> getDistancesFromLocations(Address deliveryAddress, List<Address> locations) {

        var addressList = Stream.concat(Stream.of(deliveryAddress), locations.stream()).toList();
        var request = LocationRequestDto.builder().locations(addressList).build();
        var response = restTemplate.postForObject(apiHost + "/routematrix?key=" + apiKey, request, RouteMatrixDto.class);
        if (response == null) {
            throw new RouteMatrixApiException();
        }
        return response.getDistance();
    }
}
