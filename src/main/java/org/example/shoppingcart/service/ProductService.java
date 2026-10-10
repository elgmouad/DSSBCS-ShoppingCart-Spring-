package org.example.shoppingcart.service;

import jakarta.transaction.Transactional;
import org.example.shoppingcart.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Summary description of the ProductService class.
 *
 * <p>Detailed architectural description explaining the role of this component.</p>
 *
 * @author EL GOUAL MOUAD
 * @version 1.0.0
 * @since 2026-10-06
 */
public interface ProductService {

    List<Product> getAllProducts();
    Optional<Product> getProductById(Long id);
    Product saveProduct(Product product);
    void deleteProduct(Long id);
    List<Product> searchProducts(String name, BigDecimal minPrice, BigDecimal maxPrice);
//    List<Product> findByName(String name);
//    List<Product> findByPriceRange(BigDecimal min, BigDecimal max);

}
