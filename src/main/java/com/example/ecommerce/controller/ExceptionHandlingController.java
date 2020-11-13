package com.example.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class ExceptionHandlingController {

    @ExceptionHandler(Exception.class)
    public String generalError(Model model, Exception ex) {
        model.addAttribute("title", ex.getCause());
        return "error/404";
    }
}
