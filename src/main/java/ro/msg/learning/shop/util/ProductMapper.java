package ro.msg.learning.shop.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.ProductDto;
import ro.msg.learning.shop.model.Category;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Supplier;

@RequiredArgsConstructor
@Component
public class ProductMapper {

    public Product toEntity(ProductDto productDto, Category category, Supplier supplier) {
        return Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .weight(productDto.getWeight())
                .category(category)
                .supplier(supplier)
                .build();
    }

    public ProductDto toDto(Product product) {
        return ProductDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .weight(product.getWeight())
                .categoryId(product.getCategory().getId())
                .supplierId(product.getSupplier().getId())
                .build();
    }
}
