package com.example.core.controller;

import org.springframework.stereotype.Controller;

@Controller
public class MainController {
    public String mainPage() {
        return "index";
    }
}
