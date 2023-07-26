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
import ro.msg.learning.shop.strategy.MostAbundantStrategy;
import ro.msg.learning.shop.util.StockMapper;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MostAbundantStrategyTest extends AbstractStrategyTest{

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private MostAbundantStrategy mostAbundantStrategy;

    private StockMapper stockMapper;

    @BeforeEach
    public void setUp() {
        this.stockMapper = new StockMapper();
        mostAbundantStrategy = new MostAbundantStrategy(stockRepository, stockMapper);
        super.setUp();
    }

    @Test
    public void testFindStocks_WithAbundantStrategy_MostAbundantStocksFound() {

        when(stockRepository.findByProductIdAndQuantityMostAbundant(any(UUID.class), anyInt()))
                .thenReturn(stock2).thenReturn(stock4);

        List<StockDto> actualResult = mostAbundantStrategy.findLocation(orderedProducts);

        List<StockDto> expectedResult = mostAbundantStocks.stream()
                .map(s -> stockMapper.toDto(s))
                .toList();

        assertThat(actualResult).hasSize(expectedResult.size());
        Assertions.assertEquals(actualResult, expectedResult);

    }
}
