package ro.msg.learning.shop.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.msg.learning.shop.controller.StockExportController;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.service.StockExportService;
import ro.msg.learning.shop.util.StockMapper;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StockExportControllerTest {

    @Mock
    private StockExportService stockExportService;

    @Mock
    private StockMapper stockMapper;

    @InjectMocks
    private StockExportController stockExportController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExportStocksByLocation() {
        // Arrange
        UUID locationId = UUID.randomUUID();
        Stock stock = new Stock();
        StockDto stockDto = new StockDto();
        when(stockExportService.findAllStocksByLocation(locationId)).thenReturn(Collections.singletonList(stock));
        when(stockMapper.toDto(stock)).thenReturn(stockDto);

        // Act
        ResponseEntity<List<StockDto>> response = stockExportController.exportStocksByLocation(locationId);

        // Assert
        assertEquals(HttpStatus.OK, ((ResponseEntity<?>) response).getStatusCode());
        HttpHeaders headers = response.getHeaders();
        assertEquals("attachment; filename=stocks.csv", headers.getFirst(HttpHeaders.CONTENT_DISPOSITION));

        List<StockDto> stocks = response.getBody();
        assertEquals(1, stocks.size());
        assertEquals(stockDto, stocks.get(0));
    }
}