package com.deporte.plantilla.controller;

import com.tgf.model.Agentes;
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
@RequestMapping("/agentes")
public class AgentesController {

    @Autowired
    private IUsuarioRepository repoU;
    @Autowired
    private IAgentesRepository repoA;

    @Autowired
    private IEquipoRepository repoE;
    @Autowired
    private IJugadorRepository repoJ;

    /* ABRIR LISTA DE AGENTES LIBRES */
    @GetMapping("/libres")
    public String abrirListadoAgentes(@ModelAttribute Usuario usuarioAct, Equipo equipo, Jugador jugador, HttpSession session,
                                      Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        if(repoJ.findByCodestado(2) == null){
            model.addAttribute("mensaje", "No hay Agentes libres por el momento");
        }else {
            model.addAttribute("lstAgentes", repoJ.findByCodestado(2));
        }

        return "agentes/agentes";
    }

    @PostMapping("/solicitar")
    public String solicitarAgentes(@ModelAttribute Usuario usuarioAct, Agentes agentes, Jugador jugador, HttpSession session,
                                   Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        int codjug = jugador.getCodjugador();
        int codequ = repoE.findByUsuarioAndCodestado(usuarioAct.getUsuario(),1).getCodequipo();

        if(repoA.findByCodjugadorAndCodequipoAndCodestado(codjug,codequ,1) != null){
            model.addAttribute("mensaje", "Ya Existe una solicitud a éste jugador");
        }else{
            try {
                agentes.setCodjugador(codjug);
                agentes.setCodequipo(codequ);
                agentes.setFecreg(Fecha.fechaActual());
                agentes.setCodestado(1);
                repoA.save(agentes);
                model.addAttribute("mensaje", "Solicitud enviada con éxito al jugador");

            }catch (Exception e){
                model.addAttribute("mensaje", "Error al enviar solicitud");
            }
        }

        model.addAttribute("lstAgentes", repoJ.findByCodestado(2));

        return "agentes/agentes";
    }

}
