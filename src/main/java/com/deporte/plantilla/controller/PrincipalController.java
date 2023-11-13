package com.deporte.plantilla.controller;


import com.deporte.plantilla.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class PrincipalController {

    @GetMapping("/users")
    public String abrirLoginUsuarios(Model model) {

        model.addAttribute("usuario", new Usuario());

        return "users";
    }

    @GetMapping("/logout")
    public String salir(@ModelAttribute Usuario usuario, HttpSession session, Model model) {

        session.setAttribute("correo", null);
        session.setAttribute("rol", null);

        return "usuario";
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
