package ro.msg.learning.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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

        return productMapper.toDto(productService.createProduct(productDto));
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

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {

        UUID id = productService.readById(productDto.getId()).getId();
        return productMapper.toDto(productService.updateProduct(id, productDto));
    }

}
