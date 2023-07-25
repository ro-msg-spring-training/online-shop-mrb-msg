package ro.msg.learning.shop.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.model.Address;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.util.CsvConverter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CsvConverterTest {

    public static final String CSV_HEADER = "location,product,quantity\n";
    private static final String CSV_AS_STRING = CSV_HEADER + """
            TM,Bread,10
            TM,Cola,10
            """;

    private static final String CSV_AS_STRING_NO_STOCKS = CSV_HEADER + """
            TM,Bread,0
            TM,Cola,0
            """;

    @InjectMocks
    private CsvConverter csvConverter;

    @Test
    void toCsv_withMultipleStocks_shouldConvertToCsvWithStocks() throws IOException {
        var outputStream = new ByteArrayOutputStream();
        csvConverter.toCsv(StockDto.class, generateStockList(), outputStream);
        assertThat(outputStream).hasToString(CSV_AS_STRING);
    }

    @Test
    void toCsv_withNoStock_shouldConvertToCsvWithStockZero() throws IOException {
        var outputStream = new ByteArrayOutputStream();
        csvConverter.toCsv(StockDto.class, generateStockListWithStockZero(), outputStream);
        assertThat(outputStream).hasToString(CSV_AS_STRING_NO_STOCKS);
    }

    @Test
    void fromCsv_WithEmptyHeader_shouldConvertToEmptyList() throws IOException {
        var inputStream = new ByteArrayInputStream(CSV_HEADER.getBytes());
        List<StockDto> stockList = csvConverter.fromCsv(StockDto.class, inputStream);
        assertThat(stockList).isEqualTo(List.of());
    }

    private List<StockDto> generateStockList() {

        Location location = Location.builder().name("TM").address(Address.builder().build()).build();

        Product product1 = Product.builder().name("Bread").build();
        Product product2 = Product.builder().name("Cola").build();

        return List.of(
                StockDto.builder().location(location).product(product1).quantity(10).build(),
                StockDto.builder().location(location).product(product2).quantity(10).build());
    }

    private List<StockDto> generateStockListWithStockZero() {

        Location location = Location.builder().name("TM").build();

        Product product1 = Product.builder().name("Bread").build();
        Product product2 = Product.builder().name("Cola").build();

        return List.of(
                StockDto.builder().location(location).product(product1).quantity(0).build(),
                StockDto.builder().location(location).product(product2).quantity(0).build());
    }
}