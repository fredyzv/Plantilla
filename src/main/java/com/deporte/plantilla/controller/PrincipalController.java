package com.deporte.plantilla.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PrincipalController {



    @GetMapping("/")
    public String verPaginaInicio( Model model ){

        return "index";
    }



    @GetMapping("/errores/403")
    public String error(Model model){

        return "error";
    }
}
