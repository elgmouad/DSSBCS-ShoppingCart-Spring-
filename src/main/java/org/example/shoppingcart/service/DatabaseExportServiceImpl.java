package org.example.shoppingcart.service;

import org.example.shoppingcart.model.Product;
import org.example.shoppingcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Summary description of the DatabaseExportServiceImpl class.
 *
 * <p>Detailed architectural description explaining the role of this component.</p>
 *
 * @author EL GOUAL MOUAD
 * @version 1.0.0
 * @since 2026-10-06
 */

@Service
public class DatabaseExportServiceImpl implements DatabaseExportService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Exports the product list as an SQL script containing one INSERT statement per product.
     */
    @Override
    public byte[] exportDatabaseToSql() {
        List<Product> products = productRepository.findAll();

        StringBuilder sql = new StringBuilder();
        sql.append("-- =============================================\n");
        sql.append("-- Products Export Script\n");
        sql.append("-- Generated: ").append(LocalDateTime.now()).append("\n");
        sql.append("-- Total products: ").append(products.size()).append("\n");
        sql.append("-- =============================================\n\n");

        for (Product p : products) {
            sql.append("INSERT INTO products (id, name, price) VALUES (")
                    .append(p.getId()).append(", ")
                    .append("'").append(escape(p.getName())).append("', ")
                    .append(p.getPrice() != null ? p.getPrice().toPlainString() : "NULL")
                    .append(");\n");
        }

        return sql.toString().getBytes(StandardCharsets.UTF_8);
    }

    private String escape(String value) {
        return value.replace("'", "''");
    }
}
