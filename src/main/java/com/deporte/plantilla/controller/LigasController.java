package com.deporte.plantilla.controller;

import com.tgf.Service.UbigeoService;
import com.tgf.model.Equipo;
import com.tgf.model.Liga;
import com.tgf.model.Ubigeo;
import com.tgf.model.Usuario;
import com.tgf.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/ligas")
public class LigasController {

    @Autowired
    private UbigeoService servicio;
    @Autowired
    private IUsuarioRepository repoU;
    @Autowired
    private ILigaRepository repoL;
    @Autowired
    private IEquipoRepository repoE;
    @Autowired
    private IEstadoRepository repoEs;

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

    @RequestMapping("/listaDistritos")
    @ResponseBody
    public List<String> listaDistritos(Ubigeo ubigeo){
        return servicio.traeDistritos(ubigeo);
    }

    public String contarEquipo(int codliga){
        int num = repoE.findByCodliga(codliga).size();
        Liga liga = repoL.findById(codliga).get();
        liga.setNum(num);
        repoL.save(liga);
        return "Numero actualizado";
    };

    /*CARGAR VISTA DE LIGAS*/
    @GetMapping("/ligas")
    public String abrirLigas(@ModelAttribute Usuario usuarioAct, Equipo equipo,Liga liga, HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        model.addAttribute("lstLigas", repoL.findAll());

        return "ligas/ligas";
    }

    /* NUEVA LIGA */
    @GetMapping("/nuevo")
    public String abrirNuevaLiga(@ModelAttribute Usuario usuarioAct, Liga liga, HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);
        model.addAttribute("liga", liga);

        return "ligas/nuevo";
    }

    @PostMapping("/grabar")
    public String grabarRegistroLiga(@ModelAttribute Usuario usuarioAct, Usuario usuario, Liga liga, HttpSession session, Equipo equipo, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);
        model.addAttribute("liga", liga);

        try {
            liga.setCodestado(1);
            liga.setNum(0);
            repoL.save(liga);
            model.addAttribute("mensaje", "Equipo Registrado");

        } catch (Exception e) {

            model.addAttribute("mensaje", "Error al Registrar");

        }

        model.addAttribute("lstLiga", repoL.findAll());

        return "ligas/nuevo";
    }

    /*ACTUALIZAR LIGAS*/
    @PostMapping("/actualizar")
    public String abrirActualizacionLiga(@ModelAttribute Usuario usuarioAct, HttpSession session, Liga liga, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        liga = repoL.findById(liga.getCodliga()).get();

        model.addAttribute("liga", liga);
        model.addAttribute("lstLiga", repoL.findByCodestado(1));
        model.addAttribute("lstEstado", repoEs.findAll());

        return "ligas/actualizar";
    }

    @PostMapping("/actualizado")
    public String ActualizacionLiga(@ModelAttribute Usuario usuarioAct, Liga liga, HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);

        try {
            repoL.save(liga);
            model.addAttribute("mensaje", "Equipo Actualizado");

        } catch (Exception e) {

            model.addAttribute("mensaje", "Error al Actualizar");

        }

        model.addAttribute("lstLiga", repoL.findByCodestado(1));
        model.addAttribute("liga", liga);
        model.addAttribute("lstEstado", repoEs.findAll());

        return "redirect:/ligas/ligas";
    }

    /*ELIMINAR LIGA*/
    @PostMapping("/eliminar")
    public String EliminarLiga(@ModelAttribute Usuario usuario, Liga liga, HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        Usuario usuarioAct = repoU.findByCorreo(correo);

        try {
            liga = repoL.findById(liga.getCodliga()).get();
            liga.setCodestado(2);
            repoL.save(liga);
            model.addAttribute("mensaje", "Equipo Inactivo");

        } catch (Exception e) {

            model.addAttribute("mensaje", "Error al Eliminar");
        }

        return "redirect:/ligas/ligas";
    }
}
