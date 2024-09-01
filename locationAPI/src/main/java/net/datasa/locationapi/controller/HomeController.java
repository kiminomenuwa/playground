package net.datasa.locationapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("")
    public String home() {
        return "home";
    }

    @GetMapping("mylocation1")
    public String mylocation1() {
        return "mylocation1";
    }
}
