package ro.msg.learning.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.dto.ProductDto;
import ro.msg.learning.shop.service.ProductService;
import ro.msg.learning.shop.util.ProductMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) {

        return productMapper.toDto(
                productService.createProduct(productMapper.toEntity(productDto), productDto.getCategoryId(), productDto.getSupplierId()));
    }

    @GetMapping
    public List<ProductDto> readAllProducts() {
        return productService.readAllProducts()
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductDto readById(@PathVariable UUID id) {
        return productMapper.toDto(productService.readById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable UUID id) {

        productService.deleteProduct(id);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable UUID id, @RequestBody ProductDto productDto) {

        return productMapper.toDto(
                productService.updateProduct(id, productMapper.toEntity(productDto), productDto.getCategoryId(), productDto.getSupplierId()));
    }

}
