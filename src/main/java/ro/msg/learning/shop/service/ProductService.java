package ro.msg.learning.shop.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.ResourceNotFoundException;
import ro.msg.learning.shop.model.Product;
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

    public Product createProduct(Product product, UUID categoryId, UUID supplierId) {

        product.setCategory(categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
        product.setSupplier(supplierRepository.findById(supplierId).orElseThrow(() -> new ResourceNotFoundException("Product not found")));

        return productRepository.save(product);

    }

    public Product updateProduct(UUID id, Product product, UUID categoryId, UUID supplierId) {

        Product toBeUpdated = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        toBeUpdated.setName(product.getName());
        toBeUpdated.setDescription(product.getDescription());
        toBeUpdated.setPrice(product.getPrice());
        toBeUpdated.setWeight(product.getWeight());
        toBeUpdated.setCategory(categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
        toBeUpdated.setSupplier(supplierRepository.findById(supplierId).orElseThrow(() -> new ResourceNotFoundException("Product not found")));

        return productRepository.save(toBeUpdated);

    }

    public void deleteProduct(UUID id) {

        productRepository.deleteById(id);

    }

    public Product readById(UUID id) {

        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public List<Product> readAllProducts() {

        return productRepository.findAll();
    }
}
