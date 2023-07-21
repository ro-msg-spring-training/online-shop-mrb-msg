package ro.msg.learning.shop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.strategy.LocationStrategy;
import ro.msg.learning.shop.strategy.MostAbundantStrategy;
import ro.msg.learning.shop.strategy.SingleLocationStrategy;
import ro.msg.learning.shop.util.StockMapper;

@Configuration
public class LocationConfiguration {

    @Bean
    public LocationStrategy singleLocationStrategy(StockRepository stockRepository, ProductRepository productRepository) {
        return new SingleLocationStrategy(stockRepository, productRepository);
    }

    @Bean
    public LocationStrategy abundantLocationStrategy(StockRepository stockRepository, ProductRepository productRepository, StockMapper stockMapper) {
        return new MostAbundantStrategy(stockRepository, productRepository, stockMapper);
    }

}
