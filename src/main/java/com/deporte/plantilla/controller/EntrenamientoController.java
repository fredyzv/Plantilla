package com.deporte.plantilla.controller;

import com.deporte.plantilla.model.Usuario;
import com.deporte.plantilla.repository.IUsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/entrenamiento")
public class EntrenamientoController {
    @Autowired
    private IUsuarioRepository repoU;
    @GetMapping("/listar")
    public String abrirEntrenamiento(Model model, Usuario usuarioAct, HttpSession session) {
        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);


        return "entrenamiento/entrenamiento";
    }
}
