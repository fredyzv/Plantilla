package com.deporte.plantilla.controller;


import com.deporte.plantilla.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrincipalController {

    @GetMapping("/users")
    public String abrirLoginUsuarios(Model model) {

        model.addAttribute("usuario", new Usuario());

        return "users";
    }

/*

    @GetMapping("/")
    public String verPaginaInicio( Model model ){

        return "index";
    }



    @GetMapping("/errores/403")
    public String error(Model model){

        return "error";
    }*/
}
