package com.deporte.plantilla.controller;

import com.deporte.plantilla.model.*;
import com.deporte.plantilla.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/partido")
public class PartidoController {

    @Autowired
    private IUsuarioRepository repoU;
    @Autowired
    private IEquipoRepository repoE;
    @Autowired
    private IJugadorRepository repoJ;
    @Autowired
    private IPartidoRepository repoP;
    @Autowired
    private ITemporadaRepository repoT;
    @Autowired
    private ICategoriaRepository repoC;
    @Autowired
    private ILigaRepository repoL;


    /* ABRIR PAGINA DE PARTIDOS */
    @GetMapping("/listado")
    public String abrirListadoPartidos(@ModelAttribute Usuario usuarioAct, Equipo equipo, Partido partido, HttpSession session,
                                       Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        equipo = repoE.findByUsuarioAndCodestado(usuarioAct.getUsuario(),1);
        model.addAttribute("equipo", equipo);

        if(usuarioAct.getCodrol()!=2){
            model.addAttribute("lstPartido", repoP.findAll());
        }else {
            model.addAttribute("lstPartido", repoP.findByCodequipo(equipo.getCodequipo()));
        }

        return "partido/listado";
    }

    /* NUEVO PARTIDO */
    @GetMapping("/nuevo")
    public String abrirNuevoPartido(@ModelAttribute Usuario usuarioAct, Equipo equipo, Partido partido, HttpSession session, Model model, Temporada temporada) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        equipo = repoE.findByUsuarioAndCodestado(usuarioAct.getUsuario(),1);

        String url=null;

        if(equipo == null){
            model.addAttribute("lstEquipo", repoE.findAll());
            model.addAttribute("mostrar", "no");
            url= "equipo/equipo";
        }
        if(equipo!=null) {
            model.addAttribute("equipo", equipo);
            model.addAttribute("partido", partido);
            model.addAttribute("temporada", temporada);
            model.addAttribute("lstTemporada", repoT.findAll());
            model.addAttribute("lstCategoria", repoC.findAll());
            model.addAttribute("lstLiga", repoL.findByCodestado(1));
            url= "partido/nuevo";
        }
        double sv,av,ad,ac =0.0;
        return url;
    }

    @PostMapping("/grabar")
    public String grabarRegistro(@ModelAttribute Usuario usuarioAct, Equipo equipo, Partido partido, HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        equipo = repoE.findByUsuarioAndCodestado(usuarioAct.getUsuario(),1);

        model.addAttribute("equipo", equipo);

        try {
            partido.setCodequipo(equipo.getCodequipo());
            repoP.save(partido);
            model.addAttribute("mensaje", "Partido Registrado");

        } catch (Exception e) {

            model.addAttribute("mensaje", "Error al Registrar");

        }

        model.addAttribute("partido", partido);
        model.addAttribute("lstTemporada", repoT.findAll());
        model.addAttribute("lstCategoria", repoC.findAll());
        model.addAttribute("lstLiga", repoL.findByCodestado(1));

        return "partido/nuevo";
    }

    /*ACTUALIZAR PARTIDO*/
    @PostMapping("/actualizar")
    public String abrirActualizacionPartido(@ModelAttribute Usuario usuarioAct, Equipo equipo, Partido partido, HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        equipo = repoE.findByUsuarioAndCodestado(usuarioAct.getUsuario(),1);
        partido = repoP.findById(partido.getCodpartido()).get();

        model.addAttribute("equipo", equipo);
        model.addAttribute("partido", partido);

        model.addAttribute("partido", partido);
        model.addAttribute("lstTemporada", repoT.findAll());
        model.addAttribute("lstCategoria", repoC.findAll());
        model.addAttribute("lstLiga", repoL.findByCodestado(1));


        return "partido/actualizar";
    }

    @PostMapping("/actualizado")
    public String ActualizacionEquipo(@ModelAttribute Usuario usuarioAct, Equipo equipo, Partido partido, HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        try {
            repoP.save(partido);
            model.addAttribute("mensaje", "Partido Actualizado");

        } catch (Exception e) {

            model.addAttribute("mensaje", "Error al Actualizar");

        }

        model.addAttribute("partido", partido);
        model.addAttribute("lstTemporada", repoT.findAll());
        model.addAttribute("lstCategoria", repoC.findAll());
        model.addAttribute("lstLiga", repoL.findByCodestado(1));

        return "partido/actualizar";
    }

    /* VER PARTIDO */
    @PostMapping("/ver")
    public String abrirVerPartido(@ModelAttribute Usuario usuarioAct, Equipo equipo, Partido partido, HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        partido = repoP.findById(partido.getCodpartido()).get();
        equipo = repoE.findById(partido.getCodequipo()).get();

        model.addAttribute("equipo", equipo);
        model.addAttribute("partido", partido);

        return "partido/ver";
    }

    /*REGISTRAR DATOS*/
    @PostMapping("/datos")
    public String abrirDatosPartido(@ModelAttribute Usuario usuarioAct, Equipo equipo, Jugador jugador, Partido partido, HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        equipo = repoE.findByUsuarioAndCodestado(usuarioAct.getUsuario(),1);
        partido = repoP.findById(partido.getCodpartido()).get();

        //model.addAttribute("datos", repoD.findByCodpartido(partido.getCodpartido()));
        //model.addAttribute("datos", datos);
        model.addAttribute("equipo", equipo);
        model.addAttribute("partido", partido);
        //model.addAttribute("lstJugadores", repoJ.findByCodcategoriaAndCodequipo(partido.getCodcategoria(),partido.getCodequipo()));
        model.addAttribute("partido", partido);
        model.addAttribute("lstTemporada", repoT.findAll());
        model.addAttribute("lstCategoria", repoC.findAll());
        model.addAttribute("lstLiga", repoL.findByCodestado(1));


        return "partido/datos";
    }

    /*VER DATOS*/
    @PostMapping("/verdatos")
    public String abrirVerDatosPartido(@ModelAttribute Usuario usuarioAct, Equipo equipo, Partido partido, HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        partido = repoP.findById(partido.getCodpartido()).get();
        model.addAttribute("partido", partido);
        equipo = repoE.findById(partido.getCodequipo()).get();
        model.addAttribute("equipo", equipo);


        /*List<Datos> data = repoD.findByCodpartido(partido.getCodpartido());

        if(data == null){
            model.addAttribute("mensaje","No Existen Datos para este Partido");
        }else{
            model.addAttribute("datos", data);
        }*/

        return "partido/verDatos";
    }

}
