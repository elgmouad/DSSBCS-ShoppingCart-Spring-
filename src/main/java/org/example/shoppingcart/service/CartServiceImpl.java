package org.example.shoppingcart.service;

import org.example.shoppingcart.model.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Summary description of the CartServiceImpl class.
 *
 * <p>Detailed architectural description explaining the role of this component.</p>
 *
 * @author EL GOUAL MOUAD
 * @version 1.0.0
 * @since 2026-10-06
 */

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartServiceImpl implements CartService {

    private final List<Product> cartItems = new ArrayList<>();

    @Override
    public void addProduct(Product product) {
        cartItems.add(product);
    }

    @Override
    public void removeProduct(Long productId) {
        cartItems.removeIf(p -> p.getId().equals(productId));
    }

    @Override
    public List<Product> getCartItems() {
        return new ArrayList<>(cartItems);
    }

    @Override
    public BigDecimal getTotal() {
        return cartItems.stream()
                .map(Product::getPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    @Override
    public void clearCart() {
        cartItems.clear();
    }

//    @Override
//    public void removeOneProduct(Long productId) {
//        Optional<Product> first = cartItems.stream()
//                .filter(p -> p.getId().equals(productId))
//                .findFirst();
//        first.ifPresent(cartItems::remove);
//    }
//
    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    @Override
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

}
