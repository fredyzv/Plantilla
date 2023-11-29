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

    @GetMapping("/socios")
    public String abrirLoginSocios(Model model) {

        model.addAttribute("usuario", new Usuario());

        return "usuario";
    }

    @GetMapping("/jugadores")
    public String abrirLoginJugadores(Jugadores jugadores, Model model) {

        model.addAttribute("jugadores", jugadores);

        return "jugadores";
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

        return "jugadores";
    }

    @GetMapping("/registro")
    public String abrirRegistroJugadores(Model model) {

        model.addAttribute("jugadores", new Jugadores());

        return "jugadoresRegistro";
    }

    @PostMapping("/registrar")
    public String grabarRegistroJugador(@ModelAttribute Jugadores jugadores, Model model) {

        if(repoJ.findByDocumento(jugadores.getDocumento()) != null) {

            if(repoJu.findByDocumento(jugadores.getDocumento()) == null){
                try {
                    model.addAttribute("jugadores", new Jugadores());
                    jugadores.setFecreg(Fecha.fechaActual());
                    repoJu.save(jugadores);
                    model.addAttribute("mensaje", "Jugador Registrado");
                } catch (Exception e) {
                    model.addAttribute("mensaje", "Error al Registrar");
                }
            }else{
                model.addAttribute("mensaje", "El jugador ya se encuentra registrado");
            }
        }else {
            model.addAttribute("mensaje", "El jugador no está registrado en ningún equipo de éste sistema");
        }

        return "jugadoresRegistro";
    }

    /*LOGUEAR*/
    @PostMapping("/validar")
    public String validarLogin(@ModelAttribute Jugadores jugadores, HttpSession session, Model model) {

        jugadores = repoJu.findByDocumentoAndClave(jugadores.getDocumento(), jugadores.getClave());
        System.out.println(jugadores);

        if (jugadores == null) {
            model.addAttribute("mensaje", "Documento o Clave Incorrecta");
            return "jugadores";
        }
        Jugador jugador = repoJ.findByDocumento(jugadores.getDocumento());
        model.addAttribute("jugador", jugador);
        session.setAttribute("doc", jugador.getDocumento().toString());

        return "jugadores/principalJugadores";
    }

}
