package ro.msg.learning.shop.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.msg.learning.shop.exception.StrategyNotImplementedException;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.LocationService;
import ro.msg.learning.shop.strategy.GreedyStrategy;
import ro.msg.learning.shop.strategy.LocationStrategy;
import ro.msg.learning.shop.strategy.MostAbundantStrategy;
import ro.msg.learning.shop.strategy.SingleLocationStrategy;
import ro.msg.learning.shop.strategy.StrategyType;
import ro.msg.learning.shop.util.OrderMapper;
import ro.msg.learning.shop.util.RouteMatrixUtil;
import ro.msg.learning.shop.util.StockMapper;

@Configuration
public class LocationConfiguration {

    private final StockRepository stockRepository;
    private final StockMapper stockMapper;
    private final LocationRepository locationRepository;
    private final LocationService locationService;
    private final RouteMatrixUtil routeMatrixUtil;
    private final OrderMapper orderMapper;

    @Autowired
    public LocationConfiguration(StockRepository stockRepository, StockMapper stockMapper,
                                 LocationRepository locationRepository, LocationService locationService,
                                 RouteMatrixUtil routeMatrixUtil, @Value("${location.api-host}") String apiHost, OrderMapper orderMapper) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
        this.locationRepository = locationRepository;
        this.locationService = locationService;
        this.routeMatrixUtil = routeMatrixUtil;
        this.apiHost = apiHost;
        this.orderMapper = orderMapper;
    }

    @Value("${location.api-host}")
    private String apiHost;

    @Bean
    public LocationStrategy locationStrategy(@Value("${strategy.type}") StrategyType strategyType) {
        switch (strategyType) {
            case SINGLE -> {
                return new SingleLocationStrategy(stockRepository, orderMapper);
            }
            case ABUNDANT -> {
                return new MostAbundantStrategy(stockRepository, stockMapper, orderMapper);
            }
            case GREEDY -> {
                return new GreedyStrategy(locationRepository, stockRepository, stockMapper, locationService, routeMatrixUtil, orderMapper);
            }
            default -> throw new StrategyNotImplementedException("Error. No location strategy implemented");
        }
    }


}
