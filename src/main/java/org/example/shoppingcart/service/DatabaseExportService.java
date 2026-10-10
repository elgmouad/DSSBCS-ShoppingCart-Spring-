package org.example.shoppingcart.service;

/**
 * Summary description of the DatabaseExportService class.
 *
 * <p>Detailed architectural description explaining the role of this component.</p>
 *
 * @author EL GOUAL MOUAD
 * @version 1.0.0
 * @since 2026-10-06
 */

public interface DatabaseExportService {
    /**
     * Exports the product list as an SQL script containing one INSERT statement per product.
     */
    byte[] exportDatabaseToSql();

}
