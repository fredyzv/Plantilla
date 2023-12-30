package com.deporte.plantilla.controller;

import com.deporte.plantilla.model.Temporada;
import com.deporte.plantilla.model.Usuario;
import com.deporte.plantilla.repository.IEstadoRepository;
import com.deporte.plantilla.repository.ITemporadaRepository;
import com.deporte.plantilla.repository.IUsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/temporada")
public class TemporadaController {

    @Autowired
    private ITemporadaRepository repoT;
    @Autowired
    private IUsuarioRepository repoU;
    @Autowired
    private IEstadoRepository repoE;
    /*CARGAR VISTA DE TEMPORADA*/
    @GetMapping("/listar")
    public String abrirTemporada(@ModelAttribute Usuario usuarioAct, Temporada temporada, HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        model.addAttribute("lstTemporadas", repoT.findAll());

        return "temporada/listar";
    }

    /* NUEVA TEMPORADA */
    @GetMapping("/nuevo")
    public String abrirNuevaTemporada(@ModelAttribute Usuario usuarioAct, Temporada temporada, HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);
        model.addAttribute("temporada", temporada);

        return "temporada/nuevo";
    }

    @PostMapping("/grabar")
    public String grabarRegistroLiga(@ModelAttribute Usuario usuarioAct, Usuario usuario, Temporada temporada, HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);
        model.addAttribute("temporada", temporada);

        try {
            repoT.save(temporada);
            model.addAttribute("mensaje", "Temporada Registrado");

        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al Registrar");
        }

        model.addAttribute("lstTemporada", repoT.findAll());

        return "ligas/nuevo";
    }

    /*ACTUALIZAR TEMPORADA*/
    @PostMapping("/actualizar")
    public String abrirActualizacionTemporada(@ModelAttribute Usuario usuarioAct, HttpSession session, Temporada temporada, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        temporada = repoT.findById(temporada.getCodtemporada()).get();

        model.addAttribute("temporada", temporada);
        model.addAttribute("lstestado", repoE.findAll());
        return "temporada/actualizar";
    }

    @PostMapping("/actualizado")
    public String ActualizacionTemporada(@ModelAttribute Usuario usuarioAct, Temporada temporada, HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);

        try {
            repoT.save(temporada);
            model.addAttribute("mensaje", "Temporada Actualizada");
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al Actualizar");
        }

        model.addAttribute("lstTemporada", repoT.findAll());

        return "redirect:/temporada/listar";
    }
}
