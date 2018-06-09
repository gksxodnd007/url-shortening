package com.kakaopay.url_shortening.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthChecker {

    @GetMapping(value = "/healthCheck")
    public String healthCheck() {
        return "i'm runinng";
    }
}
