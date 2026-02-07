package com.platzi.pizza.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Saludar")
public class HolaMundoController {

    @GetMapping("juanjo")
    public String saludar(){
        return "Hola Juanjo";
    }
}
