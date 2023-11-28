package com.deporte.plantilla.controller;

import com.deporte.plantilla.model.*;
import com.deporte.plantilla.repository.IAgentesRepository;
import com.deporte.plantilla.repository.IJugadorRepository;
import com.deporte.plantilla.repository.IJugadoresRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/jugadores")
public class JugadoresController {

    @Autowired
    private IJugadoresRepository repoJug;
    @Autowired
    private IJugadorRepository repoJ;
    @Autowired
    private IAgentesRepository repoA;

    /*ABRIR PRINCIPAL*/
    @GetMapping("/inicio")
    public String abrirPrincipal(@ModelAttribute Jugador jugador, HttpSession session, Model model) {

        String doc = (String) session.getAttribute("doc");
        jugador = repoJ.findByDocumento(doc);

        model.addAttribute("jugador", jugador);

        return "jugadores/principalJugadores";
    }

    @GetMapping("/solicitudes")
    public String abrirSolicitudes(@ModelAttribute Jugador jugador, HttpSession session, Model model) {

        String doc = (String) session.getAttribute("doc");
        jugador = repoJ.findByDocumento(doc);
        model.addAttribute("jugador", jugador);

        if(jugador.getCodestado() == 2){
            model.addAttribute("lstAgentes", repoA.findByCodjugadorAndCodestado(jugador.getCodjugador(),1));
        }else{
            model.addAttribute("lstAgentes", repoA.findByCodjugadorAndCodestado(jugador.getCodjugador(),2));
        }
        return "jugadores/solicitudes";
    }
    /*ACEPTAR SOLICITUD*/
    @PostMapping("/aceptar")
    public String aceptarSolicitudes(@ModelAttribute Jugador jugador, Agentes agentes, Equipo equipo, HttpSession session, Model model) {

        String doc = (String) session.getAttribute("doc");
        jugador = repoJ.findByDocumento(doc);
        model.addAttribute("jugador", jugador);

        try {
            agentes = repoA.findByCodjugadorAndCodequipoAndCodestado(jugador.getCodjugador(),equipo.getCodequipo(),1);
            jugador.setCodequipo(equipo.getCodequipo());
            jugador.setCodestado(1);
            repoJ.save(jugador);
            repoA.delete(agentes);
            model.addAttribute("mensaje", "Has aceptado la solicitud con éxito");
        }catch (Exception e){
            model.addAttribute("mensaje", "Error al aceptar la solicitud");
        }

        if(jugador.getCodestado() == 2){
            model.addAttribute("lstAgentes", repoA.findByCodjugadorAndCodestado(jugador.getCodjugador(),1));
        }else{
            model.addAttribute("lstAgentes", repoA.findByCodjugadorAndCodestado(jugador.getCodjugador(),2));
        }
        return "jugadores/solicitudes";
    }

    /*DECLINAR SOLICITUD*/
    @PostMapping("/declinar")
    public String EliminarUsuario(@ModelAttribute Usuario usuarioAct, HttpSession session, Jugador jugador, Agentes agentes, Model model) {

        String doc = (String) session.getAttribute("doc");
        jugador = repoJ.findByDocumento(doc);
        model.addAttribute("jugador", jugador);

        agentes = repoA.findByCodjugadorAndCodequipoAndCodestado(jugador.getCodjugador(), agentes.getCodequipo(),1);
        System.out.println(agentes);

        try {
            repoA.delete(agentes);
            model.addAttribute("mensaje", "Solicitud Declinada");

        } catch (Exception e) {

            model.addAttribute("mensaje", "Error al Declinar Solicitud");
        }

        if(jugador.getCodestado() == 2){
            model.addAttribute("lstAgentes", repoA.findByCodjugadorAndCodestado(jugador.getCodjugador(),1));
        }else{
            model.addAttribute("lstAgentes", repoA.findByCodjugadorAndCodestado(jugador.getCodjugador(),2));
        }

        return "jugadores/solicitudes";
    }

    @GetMapping("/perfil")
    public String perfil(@ModelAttribute Jugador jugador, Equipo equipo, HttpSession session,
                         Model model) {

        String doc = (String) session.getAttribute("doc");
        jugador = repoJ.findByDocumento(doc);
        model.addAttribute("jugador", jugador);

        return "jugadores/perfil";
    }

    /*CARGAR ACTUALIZACION DE PERFIL*/
    @GetMapping("/perfilact")
    public String abrirActualizacionPerfiJugadores(@ModelAttribute Jugadores jugadores, Jugador jugador, HttpSession session, Model model) {

        String doc = (String) session.getAttribute("doc");
        jugador = repoJ.findByDocumento(doc);
        jugadores = repoJug.findByDocumento(doc);
        model.addAttribute("jugador", jugador);
        model.addAttribute("jugadores", jugadores);

        return "jugadores/actperfil";
    }

    /*GRABAR ACTUALIZACION DE PERFIL*/
    @PostMapping("/perfilgra")
    public String actualizarPerfilJugadores(@ModelAttribute Jugadores jugadores, Jugador jugador,HttpSession session, Model model) {

        String doc = (String) session.getAttribute("doc");
        jugador = repoJ.findByDocumento(doc);
        model.addAttribute("jugador", jugador);

        try {
            System.out.println(jugadores);
            repoJug.save(jugadores);
            model.addAttribute("mensaje", "Contraseña Actualizada");
        } catch (Exception e) {

            model.addAttribute("mensaje", "Error al Actualizar");
        }

        return "jugadores/actperfil";

    }
}
