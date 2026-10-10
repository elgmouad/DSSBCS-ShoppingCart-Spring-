package org.example.shoppingcart;

import org.example.shoppingcart.model.Product;
import org.example.shoppingcart.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class ShoppingCartApplication {

    private static final Logger log = LoggerFactory.getLogger(ShoppingCartApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ShoppingCartApplication.class, args);
    }

    @Bean
    CommandLineRunner seedProducts(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() > 0) {
                log.info("Products already present ({}), skipping seed.", productRepository.count());
                return;
            }

            productRepository.saveAll(List.of(
                    Product.builder().name("Laptop HP Pavilion 15").price(new BigDecimal("899.99")).build(),
                    Product.builder().name("Smartphone Samsung Galaxy S24").price(new BigDecimal("1099.00")).build(),
                    Product.builder().name("Casque Bluetooth Sony WH-1000XM5").price(new BigDecimal("349.50")).build(),
                    Product.builder().name("Souris Logitech MX Master 3").price(new BigDecimal("99.90")).build(),
                    Product.builder().name("Clavier mécanique Keychron K2").price(new BigDecimal("119.00")).build()
                    ));

            log.info("Seeded {} sample products.", productRepository.count());

        };
    }
}
