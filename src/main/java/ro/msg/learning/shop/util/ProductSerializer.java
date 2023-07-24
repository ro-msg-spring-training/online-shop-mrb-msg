package ro.msg.learning.shop.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ro.msg.learning.shop.model.Product;

import java.io.IOException;

public class ProductSerializer extends JsonSerializer<Product> {


    @Override
    public void serialize(Product product, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(product.getName());
    }
}
