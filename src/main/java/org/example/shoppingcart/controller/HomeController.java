package org.example.shoppingcart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Summary description of the HomeController class.
 *
 * <p>Detailed architectural description explaining the role of this component.</p>
 *
 * @author EL GOUAL MOUAD
 * @version 1.0.0
 * @since 2026-10-06
 */

@Controller
public class HomeController {

    @GetMapping({"/", "/index"})
    public String home() {
        return "index";
    }

}
