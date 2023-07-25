package ro.msg.learning.shop.util;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Component
public class CsvConverter {

    private static final CsvMapper mapper = new CsvMapper();

    public static <T> List<T> fromCsv(Class<T> type, InputStream inputStream) throws IOException {
        CsvSchema schema = mapper.schemaFor(type).withHeader().withColumnReordering(true);
        MappingIterator<T> iterator = mapper.readerFor(type).with(schema).readValues(inputStream);
        return iterator.readAll();
    }

    public static <T> void toCsv(Class<T> type, List<T> data, OutputStream outputStream) throws IOException {
        CsvSchema schema = mapper.schemaFor(type).withHeader().withColumnReordering(true);
        mapper.writer(schema).writeValues(outputStream).writeAll(data);
    }
}
