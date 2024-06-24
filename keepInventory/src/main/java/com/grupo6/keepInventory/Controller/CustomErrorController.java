package com.grupo6.keepInventory.Controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
    private static final String PATH = "/error";

    @RequestMapping(PATH)
    public String handleError() {
        // Forward to custom error page
        return "error/404"; // Return the path to your custom error page
    }

    public String getErrorPath() {
        return PATH;
    }
}
