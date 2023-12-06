package com.deporte.plantilla.controller;

import com.deporte.plantilla.model.Categoria;
import com.deporte.plantilla.model.Usuario;
import com.deporte.plantilla.repository.ICategoriaRepository;
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
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private ICategoriaRepository repoC;
    @Autowired
    private IUsuarioRepository repoU;

    /*CARGAR VISTA DE CATEGORIA*/
    @GetMapping("/listar")
    public String abrirCategoria(@ModelAttribute Usuario usuarioAct, Categoria categoria, HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        model.addAttribute("lstCategoria", repoC.findAll());

        return "categoria/listar";
    }

    /* NUEVA CATEGORIA */
    @GetMapping("/nuevo")
    public String abrirNuevaCategoria(@ModelAttribute Usuario usuarioAct, Categoria categoria, HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);
        model.addAttribute("categoria", categoria);

        return "categoria/nuevo";
    }

    @PostMapping("/grabar")
    public String grabarRegistroLiga(@ModelAttribute Usuario usuarioAct, Usuario usuario, Categoria categoria, HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);
        model.addAttribute("categoria", categoria);

        try {
            repoC.save(categoria);
            model.addAttribute("mensaje", "Categoría Registrada");

        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al Registrar");
        }

        model.addAttribute("lstCategoria", repoC.findAll());

        return "categoria/nuevo";
    }

    /*ACTUALIZAR CATEGORIA*/
    @PostMapping("/actualizar")
    public String abrirActualizacionCategoria(@ModelAttribute Usuario usuarioAct, HttpSession session, Categoria categoria, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        categoria = repoC.findById(categoria.getCodcategoria()).get();

        model.addAttribute("categoria", categoria);

        return "categoria/actualizar";
    }

    @PostMapping("/actualizado")
    public String ActualizacionCategoria(@ModelAttribute Usuario usuarioAct, Categoria categoria, HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);

        try {
            repoC.save(categoria);
            model.addAttribute("mensaje", "Categoría Actualizada");
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al Actualizar");
        }

        model.addAttribute("lstCategoria", repoC.findAll());

        return "redirect:/categoria/listar";
    }
}
