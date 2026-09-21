package Pensionaten.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Controller för informationssidan om Pensionaten
@Controller
public class AboutController {

    // Visar sidan om pensionatet
    @GetMapping("/about")
    public String about() {
        return "customers/about";
    }
}
