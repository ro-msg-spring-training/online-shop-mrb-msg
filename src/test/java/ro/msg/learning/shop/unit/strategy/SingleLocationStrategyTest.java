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
import ro.msg.learning.shop.util.StockMapper;

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

    private StockMapper stockMapper;

    @BeforeEach
    public void setUp() {
        this.stockMapper = new StockMapper();
        singleLocationStrategy = new SingleLocationStrategy(stockRepository);
        super.setUp();
    }
    @Test
    public void testFindStocks_WithSingleStrategy_SingleLocationStocksFound() {

        when(stockRepository.findByProductIdAndQuantity(any(UUID.class), anyInt()))
                .thenReturn(List.of(breadStockAr)).thenReturn(List.of(colaStockAr));

        List<StockDto> actualResult = singleLocationStrategy.findLocation(orderedProducts);

        List<StockDto> expectedResult = singleLocationStocks.stream()
                .map(s -> stockMapper.toDto(s))
                .toList();

        assertThat(actualResult).hasSize(expectedResult.size());
        Assertions.assertEquals(actualResult, expectedResult);

    }

}
