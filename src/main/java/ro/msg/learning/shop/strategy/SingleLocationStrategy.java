package ro.msg.learning.shop.strategy;

import lombok.AllArgsConstructor;
import ro.msg.learning.shop.dto.ProductQuantityDto;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.exception.NoStocksAvailableException;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class SingleLocationStrategy implements LocationStrategy {

    private final StockRepository stockRepository;

    @Override
    public List<StockDto> findLocation(List<ProductQuantityDto> products) {

        Map<UUID, List<StockDto>> map = new HashMap<>();

        products.forEach(p -> {

            List<Stock> stocks = stockRepository.findByProductIdAndQuantity(
                    p.getProductId(), p.getQuantity());

            if (stocks.isEmpty()) {
                throw new NoStocksAvailableException("No stocks available");
            }

            stocks.forEach(stock -> {
                List<StockDto> list = map.get(stock.getLocation().getId());
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(new StockDto(stock.getProduct(), stock.getLocation(), p.getQuantity()));
                map.put(stock.getLocation().getId(), list);
            });
        });

        for (Map.Entry<UUID, List<StockDto>> entry : map.entrySet()) {
            if (entry.getValue().size() == products.size()) {
                return entry.getValue();
            }
        }

        List<StockDto> list = map.entrySet().stream()
                .filter(e -> e.getValue().size() == products.size())
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(new ArrayList<>())
                .stream().toList();

        return list;

    }
}
