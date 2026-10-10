package org.example.shoppingcart.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Summary description of the ErrorController class.
 *
 * <p>Detailed architectural description explaining the role of this component.</p>
 *
 * @author EL GOUAL MOUAD
 * @version 1.0.0
 * @since 2026-10-11
 */

@Controller
public class ErrorController implements org.springframework.boot.webmvc.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        // Code HTTP de l'erreur (404, 403, 500...)
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        // URL d'origine demandée par l'utilisateur (ex: /expo)
        Object path = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

        model.addAttribute("status", status);
        model.addAttribute("path", path);

        if (status != null && Integer.parseInt(status.toString()) == 404) {
            return "error/404";      // templates/error/404.html
        }
        return "error/error";        // templates/error/404.html (page générique)
    }
}
