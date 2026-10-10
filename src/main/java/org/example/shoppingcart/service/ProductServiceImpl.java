package org.example.shoppingcart.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.shoppingcart.model.Product;
import org.example.shoppingcart.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Summary description of the ProductServiceImpl class.
 *
 * <p>Detailed architectural description explaining the role of this component.</p>
 *
 * @author EL GOUAL MOUAD
 * @version 1.0.0
 * @since 2026-10-06
 */

@Service
//@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> searchProducts(String name, BigDecimal minPrice, BigDecimal maxPrice) {
        String normalizedName = (name != null && !name.isBlank()) ? name : null;
        return productRepository.searchProducts(normalizedName, minPrice, maxPrice);
    }

//    @Override
//    public List<Product> findByName(String name) {
//        return productRepository.findByNameContainingIgnoreCase(name);
//    }
//
//    @Override
//    public List<Product> findByPriceRange(BigDecimal min, BigDecimal max) {
//        return productRepository.findByPriceBetween(min,max);
//    }
}
