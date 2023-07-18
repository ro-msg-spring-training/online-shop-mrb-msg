package ro.msg.learning.shop.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.ProductDto;
import ro.msg.learning.shop.model.Category;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Supplier;
import ro.msg.learning.shop.repository.CategoryRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.SupplierRepository;
import ro.msg.learning.shop.util.ProductMapper;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;

    public Product createProduct(ProductDto productDto) {

        Category category = categoryRepository.findById(productDto.getCategoryId()).get();
        Supplier supplier = supplierRepository.findById(productDto.getSupplierId()).get();

        return productRepository.save(productMapper.toEntity(productDto, category, supplier));

    }

    public Product updateProduct(UUID id, ProductDto productDto) {

        Category category = categoryRepository.findById(productDto.getCategoryId()).get();
        Supplier supplier = supplierRepository.findById(productDto.getSupplierId()).get();

        Product product = productRepository.findById(id).get();

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setWeight(productDto.getWeight());
        product.setCategory(category);
        product.setSupplier(supplier);

        return productRepository.save(product);

    }

    public void deleteProduct(UUID id) {

        productRepository.deleteById(id);

    }

    public Product readById(UUID id) {

        return productRepository.findById(id).get();
    }

    public List<Product> readAllProducts() {

        return productRepository.findAll();
    }
}
