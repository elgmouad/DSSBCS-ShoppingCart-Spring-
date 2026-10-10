package org.example.shoppingcart.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingcart.model.Product;
import org.example.shoppingcart.service.DatabaseExportService;
import org.example.shoppingcart.service.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Summary description of the AdminController class.
 *
 * <p>Detailed architectural description explaining the role of this component.</p>
 *
 * @author EL GOUAL MOUAD
 * @version 1.0.0
 * @since 2026-10-06
 */

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;
    private final DatabaseExportService databaseExportService;

    public AdminController(ProductService productService, DatabaseExportService databaseExportService) {
        this.productService = productService;
        this.databaseExportService = databaseExportService;
    }

    @GetMapping
    public String adminPanel(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("product", new Product());
        return "admin";
    }

    /** Download the products as an SQL script. */
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportDatabase() throws IOException {
        byte[] sqlBytes = databaseExportService.exportDatabaseToSql();

        Path target = Path.of("exports","products.sql");
        Files.createDirectories(target.getParent());
        Files.write(target,sqlBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData(
                "attachment", "products.sql");
        return ResponseEntity.ok()
                .headers(headers)
                .body(sqlBytes);
    }
}
