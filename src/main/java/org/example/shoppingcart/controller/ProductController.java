package org.example.shoppingcart.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shoppingcart.model.Product;
import org.example.shoppingcart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

/**
 * Summary description of the ProductController class.
 *
 * <p>Detailed architectural description explaining the role of this component.</p>
 *
 * @author EL GOUAL MOUAD
 * @version 1.0.0
 * @since 2026-10-06
 */

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /** Public: list products with optional search / price filtering. */
    @GetMapping
    public String listProducts(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(value = "category", required = false) String category,
            Model model) {

        List<Product> products = productService.searchProducts(name, minPrice, maxPrice);//, category);
        model.addAttribute("products", products);
        model.addAttribute("name", name);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("category", category);
        return "productos";
    }

    /** Authenticated: show add form. */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("isEdit", false);
        return "formulario-producto";
    }

    /** Authenticated: show edit form. */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return productService.getProductById(id)
                .map(p -> {
                    model.addAttribute("product", p);
                    model.addAttribute("isEdit", true);
                    return "formulario-producto";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("errorMessage", "Producto no encontrado");
                    return "redirect:/products";
                });
    }

    /** Authenticated: save a new or edited product. */
    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product,
                              BindingResult result,
                              RedirectAttributes ra,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", product.getId() != null);
            return "formulario-producto";
        }
        boolean isEdit = product.getId() != null;
        productService.saveProduct(product);
        ra.addFlashAttribute("successMessage",
                isEdit ? "Producto actualizado correctamente" : "Producto agregado correctamente");
        return "redirect:/products";
    }

    /** Authenticated: delete a product. */
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes ra) {
        productService.deleteProduct(id);
        ra.addFlashAttribute("successMessage", "Producto eliminado correctamente");
        return "redirect:/products";
    }

}
