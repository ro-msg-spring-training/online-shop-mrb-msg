package ro.msg.learning.shop.strategy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ro.msg.learning.shop.dto.ProductQuantityDto;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.exception.NoStocksAvailableException;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.util.StockMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
public class GreedyStrategy implements LocationStrategy {

    private LocationRepository locationRepository;
    private StockRepository stockRepository;
    private StockMapper stockMapper;

    @Override
    public List<StockDto> findLocation(List<ProductQuantityDto> products, List<BigDecimal> distances) {

        List<StockDto> toBeOrdered = new ArrayList<>();

        List<Location> allLocations = locationRepository.findAll();
        List<DistanceToDeliveryAddress> distanceToLocations = mapDistancesToDto(distances, allLocations);

        distanceToLocations
                .forEach(l -> {
                    Set<StockDto> availableStocksPerLocation = getAvailableStocksPerLocation(l.getLocation(), products);
                    if (availableStocksPerLocation != null) {
                        if (availableStocksPerLocation.size() == products.size()) {
                            toBeOrdered.addAll(availableStocksPerLocation);
                        }
                    }
                });

        if (toBeOrdered.size() == 0) throw new NoStocksAvailableException();

        return toBeOrdered.subList(0, products.size());
    }


    private Set<StockDto> getAvailableStocksPerLocation(Location location, List<ProductQuantityDto> products) {

        Set<Stock> stocks = products.stream()
                .map(p -> stockRepository.findByProductIdAndLocationAndQuantity(p.getProductId(), location, p.getQuantity()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (stocks.size() == products.size()) {
            return stocks.stream().map(s -> stockMapper.toDto(s)).collect(Collectors.toSet());
        } else return null;

    }

    private List<DistanceToDeliveryAddress> mapDistancesToDto(List<BigDecimal> distances, List<Location> allLocations) {

        return IntStream.range(1, distances.size())
                .mapToObj(i -> DistanceToDeliveryAddress.builder()
                        .distanceFromTargetLocation(distances.get(i))
                        .location(allLocations.get(i - 1))
                        .build())
                .sorted(Comparator.comparing(DistanceToDeliveryAddress::getDistanceFromTargetLocation))
                .toList();
    }

    @Builder
    @Data
    private static class DistanceToDeliveryAddress {

        private Location location;
        private BigDecimal distanceFromTargetLocation;
    }
}
