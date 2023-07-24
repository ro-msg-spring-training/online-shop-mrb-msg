package ro.msg.learning.shop.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.service.StockExportService;
import ro.msg.learning.shop.util.StockMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stocks")
@AllArgsConstructor
public class StockExportController {

    private final StockExportService stockExportService;
    private final StockMapper stockMapper;

    @GetMapping(value = "/export/{locationId}", produces = "text/csv")
    public ResponseEntity<List<StockDto>> exportStocksByLocation(@PathVariable("locationId") UUID locationId) {
        List<StockDto> stocks = stockExportService.findAllStocksByLocation(locationId).stream().map(stockMapper::toDto).toList();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=stocks.csv");

        return ResponseEntity.ok().headers(headers).body(stocks);
    }
}
