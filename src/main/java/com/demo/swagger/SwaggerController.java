package com.demo.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by davide on 18/10/16.
 */
@Controller
public class SwaggerController {
    @RequestMapping("/")
    String home() {
        return "redirect:swagger-ui.html";
    }
}
