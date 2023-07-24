package ro.msg.learning.shop.util;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class CsvMessageConverter<T> extends AbstractGenericHttpMessageConverter<List<T>> {

    public CsvMessageConverter() {
        super(MediaType.parseMediaType("text/csv"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return List.class.isAssignableFrom(clazz);
    }

    @Override
    protected List<T> readInternal(Class<? extends List<T>> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    public List<T> read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
            throws IOException {
        InputStream inputStream = inputMessage.getBody();
        return CsvConverter.fromCsv((Class<T>) contextClass, inputStream);
    }


    @Override
    protected void writeInternal(List<T> data, Type type, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        OutputStream outputStream = outputMessage.getBody();
        CsvConverter.toCsv((Class<T>) extractClassFromType(type), data, outputStream);
        outputStream.flush();
    }

    private Class<?> extractClassFromType(Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            throw new IllegalArgumentException("Invalid type argument");
        }
    }
}
