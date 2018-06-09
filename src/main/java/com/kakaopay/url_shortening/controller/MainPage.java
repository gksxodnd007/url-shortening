package com.kakaopay.url_shortening.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPage {

    @GetMapping(value = {"/urlShortening", "/"})
    public String showMainPage() {
        return "index";
    }

}
