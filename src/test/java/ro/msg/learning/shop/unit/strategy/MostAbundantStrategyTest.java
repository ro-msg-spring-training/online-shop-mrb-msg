package ro.msg.learning.shop.unit.strategy;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.strategy.MostAbundantStrategy;
import ro.msg.learning.shop.util.OrderMapper;
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

    @Mock
    private StockMapper stockMapper;

    @Mock
    private OrderMapper orderMapper;

    @BeforeEach
    public void setUp() {
        this.stockMapper = new StockMapper();
        mostAbundantStrategy = new MostAbundantStrategy(stockRepository, stockMapper, orderMapper);
        super.setUp();
    }

    @Test
    public void testFindStocks_WithAbundantStrategy_MostAbundantStocksFound() {

        when(stockRepository.findByProductIdAndQuantityMostAbundant(any(UUID.class), anyInt()))
                .thenReturn(breadStockTm).thenReturn(colaStockTm);

        when(orderMapper.mapProductQuantityDtoToOrderDetail(any())).thenReturn(orderDetails);
        when(orderMapper.mapOrderDetailsToProductQuantityDto(any())).thenReturn(orderedProducts);

        Order order = Order.builder()
                .orderDetails(orderMapper.mapProductQuantityDtoToOrderDetail(orderedProducts))
                .build();

        List<StockDto> actualResult = mostAbundantStrategy.findLocation(order);

        List<StockDto> expectedResult = mostAbundantStocks.stream()
                .map(s -> stockMapper.toDto(s))
                .toList();

        assertThat(actualResult).hasSize(expectedResult.size());
        Assertions.assertEquals(actualResult, expectedResult);

    }
}
