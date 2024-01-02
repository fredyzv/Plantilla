package com.deporte.plantilla.controller;


import com.deporte.plantilla.model.Agentes;
import com.deporte.plantilla.model.Equipo;
import com.deporte.plantilla.model.Jugador;
import com.deporte.plantilla.model.Usuario;
import com.deporte.plantilla.repository.IAgentesRepository;
import com.deporte.plantilla.repository.IEquipoRepository;
import com.deporte.plantilla.repository.IJugadorRepository;
import com.deporte.plantilla.repository.IUsuarioRepository;
import com.deporte.plantilla.util.Fecha;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



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
            model.addAttribute("equipo", repoE.findByUsuarioAndCodestado(usuarioAct.getUsuario(),1));
            model.addAttribute("advertencia","Debe registrar primero un equipo para poder solicitar jugadores");
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
