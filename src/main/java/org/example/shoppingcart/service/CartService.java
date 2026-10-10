package org.example.shoppingcart.service;

import org.example.shoppingcart.model.Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * Summary description of the CartService class.
 *
 * <p>Detailed architectural description explaining the role of this component.</p>
 *
 * @author EL GOUAL MOUAD
 * @version 1.0.0
 * @since 2026-10-06
 */
public interface CartService {

    void addProduct(Product product) ;

    void removeProduct(Long productId) ;

    List<Product> getCartItems() ;

    BigDecimal getTotal() ;

    void clearCart();

    int getItemCount() ;

//    void removeOneProduct(Long productId);

    boolean isEmpty();
}
