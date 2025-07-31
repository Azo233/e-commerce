package com.azo.ecommerce.service.implementation;

import com.azo.ecommerce.dto.products.ProductsRequest;
import com.azo.ecommerce.model.Product;
import com.azo.ecommerce.repository.ProductsRepository;
import com.azo.ecommerce.service.KafkaService.KafkaProducerService;
import com.azo.ecommerce.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplementation implements ProductService {

    private final ProductsRepository productsRepository;
    private final KafkaProducerService kafkaProducerService;

    public ProductServiceImplementation(ProductsRepository productsRepository, KafkaProducerService kafkaProducerService) {
        this.productsRepository = productsRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public List<Product> getAllProducts() {
        return productsRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long productId) {
        return productsRepository.findById(productId);
    }

    @Override
    public Product createProduct(ProductsRequest request) {
        Product savedProduct = productsRepository.save(request.getProduct());

        String productData = """
            {
                "action": "PRODUCT_CREATED",
                "productId": %d,
                "name": "%s",
                "price": %.2f,
                "stock": %d,
                "categoryId": %d,
                "timestamp": "%s"
            }
            """.formatted(
                savedProduct.getProductId(),
                savedProduct.getName(),
                savedProduct.getPrice(),
                savedProduct.getStockQuantity(),
                savedProduct.getCategory().getCategoryId(),
                java.time.Instant.now()
        );
        kafkaProducerService.sendInventoryUpdate(productData);

        String auditData = "User created product: " + savedProduct.getName() +
                " (ID: " + savedProduct.getProductId() + ")";
        kafkaProducerService.sendAuditLog(auditData);

        return savedProduct;
    }

    @Override
    public Optional<Product> updateProduct(ProductsRequest request) {
        Product updatedProduct =  productsRepository.save(request.getProduct());

        if (!productsRepository.existsById(request.getProduct().getProductId())) {
            throw new IllegalArgumentException("Product does not exist with ID: " + request.getProduct().getProductId());
        }
        String productData = """
                {
                "action": "PRODUCT_CREATED",
                "productId": %d,
                "name": "%s",
                "price": %.2f,
                "stock": %d,
                "categoryId": %d,
                "timestamp": "%s"
                }
                """.formatted(
                updatedProduct.getProductId(),
                updatedProduct.getName(),
                updatedProduct.getPrice(),
                updatedProduct.getStockQuantity(),
                updatedProduct.getCategory().getCategoryId(),
                java.time.Instant.now()
        );
        kafkaProducerService.sendInventoryUpdate(productData);

        String auditData = "User updated product " + updatedProduct.getName() +
                " (ID: " + updatedProduct.getProductId() + ")";
        kafkaProducerService.sendAuditLog(auditData);

        return Optional.of(updatedProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        if (!productsRepository.existsById(productId)) {
            throw new IllegalArgumentException("Product does not exist with ID: " + productId);
        }
        String productData = """
                "productId": %d
                """.formatted(
                        productId
        );
        kafkaProducerService.sendInventoryUpdate(productData);
        String auditData = "User deleted product by ID " + productId;
        kafkaProducerService.sendAuditLog(auditData);
        productsRepository.deleteById(productId);
    }

    @Override
    public Product getProductByName(String productName) {
        return productsRepository.findByName(productName);
    }

    @Override
    public String getProductNameById(Long productId) {
        return String.valueOf(productsRepository.findById(productId)
        .map(Product::getName));
    }
}

