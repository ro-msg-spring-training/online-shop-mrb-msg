package ro.msg.learning.shop.unit.strategy;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.strategy.SingleLocationStrategy;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SingleLocationStrategyTest extends AbstractStrategyTest{

    @Mock
    private StockRepository stockRepository;
    @InjectMocks
    private SingleLocationStrategy singleLocationStrategy;

    @BeforeEach
    public void setUp() {
        singleLocationStrategy = new SingleLocationStrategy(stockRepository);
        super.setUp();
    }
    @Test
    public void findLocation_StocksFound() {

        when(stockRepository.findByProductIdAndQuantity(any(UUID.class), anyInt()))
                .thenReturn(resultingStocks);

        List<StockDto> result = singleLocationStrategy.findLocation(orderedProducts);

        List<Stock> resultingList = mockRepository(0, 1);

        assertThat(result).hasSize(resultingList.size());
    }


    private List<Stock> mockRepository(Integer... productIds) {
        return Stream.of(productIds).map(productId -> resultingStocks.get(productId)).toList();
    }

}
 //assertThrows(
//                NoLocationFoundException.class,
//                () -> locationStrategy.findStocksForOrder(order, null)
//        );