package Pensionaten.controller;

import Pensionaten.dto.BookingDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Controller för startsidan
@Controller
public class HomeController {

    // Hanterar både "/" och "/home"
    @GetMapping({"/", "/home"})
    public String home(org.springframework.ui.Model model) {

        // Skapar ett tomt BookingDTO objekt som används i sökformuläret på startsidan
        model.addAttribute("booking", new BookingDTO());

        // Returnerar startsidan home.html
        return "customers/home";
    }
}