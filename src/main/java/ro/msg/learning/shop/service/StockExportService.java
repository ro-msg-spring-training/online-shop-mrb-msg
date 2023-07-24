package ro.msg.learning.shop.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StockExportService {

    private StockRepository stockRepository;

    public List<Stock> findAllStocksByLocation(UUID locationId) {

        return stockRepository.findAllByLocationId(locationId);

    }
}
