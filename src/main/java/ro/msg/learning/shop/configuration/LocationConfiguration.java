package ro.msg.learning.shop.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ro.msg.learning.shop.exception.StrategyNotImplementedException;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.strategy.GreedyStrategy;
import ro.msg.learning.shop.strategy.LocationStrategy;
import ro.msg.learning.shop.strategy.MostAbundantStrategy;
import ro.msg.learning.shop.strategy.SingleLocationStrategy;
import ro.msg.learning.shop.util.StockMapper;

import java.time.Duration;

@Configuration
public class LocationConfiguration {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private LocationRepository locationRepository;

    @Bean
    public LocationStrategy locationStrategy(@Value("${strategy.type}") String strategyType) {
        switch (strategyType) {
            case "SINGLE" -> {
                return new SingleLocationStrategy(stockRepository);
            }
            case "ABUNDANT" -> {
                return new MostAbundantStrategy(stockRepository, stockMapper);
            }
            case "GREEDY" ->
            {
                return new GreedyStrategy(locationRepository, stockRepository, stockMapper);
            }
            default -> throw new StrategyNotImplementedException("Error. No location strategy implemented");
        }
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .uriTemplateHandler(new DefaultUriBuilderFactory("http://www.mapquestapi.com/directions/v2/"))
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }
}
