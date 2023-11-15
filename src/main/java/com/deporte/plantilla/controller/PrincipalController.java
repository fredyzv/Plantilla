package com.deporte.plantilla.controller;


import com.deporte.plantilla.model.Ubigeo;
import com.deporte.plantilla.model.Usuario;
import com.deporte.plantilla.service.UbigeoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PrincipalController {

    @Autowired
    private UbigeoService servicio;

    @RequestMapping("/listaDepartamentos")
    @ResponseBody
    public List<String> listaDep(){
        return servicio.traeDepartamentos();
    }

    @RequestMapping("/listaProvincias")
    @ResponseBody
    public List<String> listaProv(Ubigeo ubigeo){
        return servicio.traeProvincias(ubigeo);
    }

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
