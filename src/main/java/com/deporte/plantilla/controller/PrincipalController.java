package com.deporte.plantilla.controller;


import com.deporte.plantilla.model.Jugador;
import com.deporte.plantilla.model.Jugadores;
import com.deporte.plantilla.model.Usuario;
import com.deporte.plantilla.repository.IJugadorRepository;
import com.deporte.plantilla.repository.IJugadoresRepository;
import com.deporte.plantilla.util.Fecha;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PrincipalController {

    @Autowired
    private IJugadoresRepository repoJu;

    @Autowired
    private IJugadorRepository repoJ;

    /*Login de usuarios*/
    @GetMapping("/socios")
    public String abrirLoginSocios(Model model) {

        model.addAttribute("usuario", new Usuario());

        return "usuario";
    }
    /*Login y registro de jugadores*/
    @GetMapping("/atletas")
    public String abrirLoginJugadores(Model model) {

        model.addAttribute("jugadores", new Jugadores());

        return "atletas";
    }

    @GetMapping("/logout")
    public String salir(@ModelAttribute Usuario usuario,HttpSession session, Model model) {

        session.setAttribute("correo", null);
        session.setAttribute("rol", null);

        return "usuario";
    }

    @GetMapping("/logout2")
    public String salir2(@ModelAttribute Jugadores jugadores,HttpSession session, Model model) {

        session.setAttribute("doc", null);

        return "atletas";
    }
}
