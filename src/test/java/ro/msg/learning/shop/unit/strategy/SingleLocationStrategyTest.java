package ro.msg.learning.shop.unit.strategy;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.strategy.SingleLocationStrategy;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
                .thenReturn(resultingStockList1).thenReturn(resultingStockList2);

        List<StockDto> actualResult = singleLocationStrategy.findLocation(orderedProducts);

        assertThat(actualResult).hasSize(expectedResult.size());
        Assertions.assertEquals(actualResult, expectedResult);

    }

}
