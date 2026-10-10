package org.example.shoppingcart.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingcart.service.CartService;
import org.example.shoppingcart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Summary description of the CartController class.
 *
 * <p>Detailed architectural description explaining the role of this component.</p>
 *
 * @author EL GOUAL MOUAD
 * @version 1.0.0
 * @since 2026-10-06
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("total", cartService.getTotal());
        return "cart";
    }

    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable Long id, RedirectAttributes ra) {
        productService.getProductById(id).ifPresentOrElse(
                cartService::addProduct,
                () -> ra.addFlashAttribute("errorMessage", "Producto no encontrado")
        );
        if (!ra.getFlashAttributes().containsKey("errorMessage")) {
            ra.addFlashAttribute("successMessage", "Producto agregado al carrito");
        }
        return "redirect:/cart";
    }

    @PostMapping("/remove/{id}")
    public String removeFromCart(@PathVariable Long id, RedirectAttributes ra) {
        cartService.removeProduct(id);
        ra.addFlashAttribute("successMessage", "Producto eliminado del carrito");
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(RedirectAttributes ra) {
        cartService.clearCart();
        ra.addFlashAttribute("successMessage", "Carrito vaciado");
        return "redirect:/cart";
    }
}
