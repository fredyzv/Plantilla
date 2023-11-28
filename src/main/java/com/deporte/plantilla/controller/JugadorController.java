package com.deporte.plantilla.controller;

import com.tgf.model.Equipo;
import com.tgf.model.Jugador;
import com.tgf.model.Usuario;
import com.tgf.repository.*;
import com.tgf.util.Fecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/jugador")
public class JugadorController {

    @Autowired
    private IUsuarioRepository repoU;
    @Autowired
    private IEquipoRepository repoE;
    @Autowired
    private IEstadoRepository repoEs;
    @Autowired
    private IDocumentoRepository repoD;
    @Autowired
    private ISexoRepository repoS;
    @Autowired
    private ICategoriaRepository repoC;
    @Autowired
    private IPosicionRepository repoP;
    @Autowired
    private IJugadorRepository repoJ;

    /* ABRIR LISTA DE JUGADORES */
    @GetMapping("/jugador")
    public String abrirListadoJugador(@ModelAttribute Usuario usuarioAct, Equipo equipo, Jugador jugador, HttpSession session,
                                        Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        if(usuarioAct.getCodrol() != 2){
            model.addAttribute("lstJugador" , repoJ.findAll());
        }else{
            equipo = repoE.findByUsuarioAndCodestado(usuarioAct.getUsuario(),1);

            if(equipo == null){
                model.addAttribute("mensaje", "Primero debes registrar un equipo");
                model.addAttribute("mostrar", "no");
                return "equipo/equipo";

            }else {
                model.addAttribute("lstJugador", repoJ.findByCodequipoAndCodestado(equipo.getCodequipo(),1));
            }
        }
        return "jugador/jugador";
    }

    /* NUEVO JUGADOR */
    @GetMapping("/nuevo")
    public String abrirNuevoJugador(@ModelAttribute Usuario usuarioAct, Equipo equipo, Jugador jugador, HttpSession session,
                                    Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        model.addAttribute("lstUsuario", repoU.findAll());
        model.addAttribute("lstEstado", repoEs.findAll());
        model.addAttribute("lstEquipo", repoE.findAll());
        model.addAttribute("lstDocumento", repoD.findAll());
        model.addAttribute("lstSexo", repoS.findAll());
        model.addAttribute("lstCategoria", repoC.findAll());
        model.addAttribute("lstPosicion", repoP.findAll());

        return "jugador/nuevo";
    }

    /* GRABAR NUEVO JUGADOR */
    @PostMapping("/grabar")
    public String grabarRegistroJugador(@ModelAttribute Usuario usuarioAct, Jugador jugador, HttpSession session,
                                 Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        model.addAttribute("jugador", jugador);

        try {

            jugador.setCodestado(1);
            jugador.setFecreg(Fecha.fechaActual());
            jugador.setCodequipo(repoE.findByUsuarioAndCodestado(usuarioAct.getUsuario(),1).getCodequipo());

            repoJ.save(jugador);
            model.addAttribute("mensaje", "Jugador Registrado");

        } catch (Exception e) {

            model.addAttribute("mensaje", "Error al Registrar");
            System.out.println("Error " + e);
        }

        model.addAttribute("lstUsuario", repoU.findAll());
        model.addAttribute("lstEstado", repoEs.findAll());
        model.addAttribute("lstEquipo", repoE.findAll());
        model.addAttribute("lstDocumento", repoD.findAll());
        model.addAttribute("lstSexo", repoS.findAll());
        model.addAttribute("lstCategoria", repoC.findAll());
        model.addAttribute("lstPosicion", repoP.findAll());

        System.out.println("Enviado " + jugador);

        return "jugador/nuevo";
    }

    /* ACTUALIZAR JUGADOR */
    @PostMapping("/actualizar")
    public String abrirActualizacionJugador(@ModelAttribute Usuario usuarioAct, HttpSession session, Jugador jugador, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        jugador = repoJ.findById(jugador.getCodjugador()).get();

        model.addAttribute("jugador", jugador);
        model.addAttribute("lstEstado", repoEs.findAll());
        model.addAttribute("lstEquipo", repoE.findAll());
        model.addAttribute("lstDocumento", repoD.findAll());
        model.addAttribute("lstSexo", repoS.findAll());
        model.addAttribute("lstCategoria", repoC.findAll());
        model.addAttribute("lstPosicion", repoP.findAll());

        return "jugador/actualizar";
    }

    @PostMapping("/actualizado")
    public String ActualizacionJugador(@ModelAttribute Usuario usuarioAct, HttpSession session, Jugador jugador, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        System.out.println("usuario " + jugador);

        try {

            repoJ.save(jugador);
            model.addAttribute("mensaje", "Jugador Actualizado");

        } catch (Exception e) {

            model.addAttribute("mensaje", "Error al Registrar");

        }

        model.addAttribute("jugador", jugador);
        model.addAttribute("lstEstado", repoEs.findAll());
        model.addAttribute("lstEquipo", repoE.findAll());
        model.addAttribute("lstDocumento", repoD.findAll());
        model.addAttribute("lstSexo", repoS.findAll());
        model.addAttribute("lstCategoria", repoC.findAll());
        model.addAttribute("lstPosicion", repoP.findAll());

        System.out.println("usuario 2 " + jugador);

        return "jugador/actualizar";
    }

    /*ELIMINAR JUGADOR*/
    @PostMapping("/eliminar")
    public String EliminarJugador(@ModelAttribute Usuario usuario, HttpSession session, Jugador jugador, Equipo equipo, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        Usuario usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        jugador = repoJ.findById(jugador.getCodjugador()).get();

        try {
            jugador.setCodestado(2);
            repoJ.save(jugador);
            model.addAttribute("mensaje", "Jugador Inactivo");
            model.addAttribute("lstJugador", repoJ.findByCodequipoAndCodestado(equipo.getCodequipo(),1));

        } catch (Exception e) {

            model.addAttribute("mensaje", "Error al Eliminar");
            model.addAttribute("lstJugador", repoJ.findByCodequipoAndCodestado(equipo.getCodequipo(),1));

        }

        return "redirect:/jugador/jugador";
    }

    /* VER JUGADOR */
    @PostMapping("/ver")
    public String abrirVerJugador(@ModelAttribute Usuario usuarioAct, HttpSession session, Jugador jugador, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        jugador = repoJ.findById(jugador.getCodjugador()).get();

        model.addAttribute("jugador", jugador);

        return "jugador/ver";
    }

}
