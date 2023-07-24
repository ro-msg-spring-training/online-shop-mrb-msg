package ro.msg.learning.shop.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.msg.learning.shop.exception.StrategyNotImplementedException;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.strategy.LocationStrategy;
import ro.msg.learning.shop.strategy.MostAbundantStrategy;
import ro.msg.learning.shop.strategy.SingleLocationStrategy;
import ro.msg.learning.shop.util.StockMapper;

@Configuration
public class LocationConfiguration {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private StockMapper stockMapper;

    @Bean
    public LocationStrategy locationStrategy(@Value("${strategy.type}") String strategyType) {
        switch (strategyType) {
            case "SINGLE" -> {
                return new SingleLocationStrategy(stockRepository);
            }
            case "ABUNDANT" -> {
                return new MostAbundantStrategy(stockRepository, stockMapper);
            }
            default -> throw new StrategyNotImplementedException("Error. No location strategy implemented");
        }
    }
}
