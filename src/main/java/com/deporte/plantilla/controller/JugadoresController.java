package com.deporte.plantilla.controller;

import com.deporte.plantilla.model.*;
import com.deporte.plantilla.repository.IAgentesRepository;
import com.deporte.plantilla.repository.IDatosRepository;
import com.deporte.plantilla.repository.IJugadorRepository;
import com.deporte.plantilla.repository.IJugadoresRepository;

import com.deporte.plantilla.util.Fecha;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;
import java.util.List;

@Controller
@RequestMapping("/jugadores")
public class JugadoresController {

    @Autowired
    private IJugadoresRepository repoJug;
    @Autowired
    private IJugadorRepository repoJ;
    @Autowired
    private IAgentesRepository repoA;
    @Autowired
    private IDatosRepository repoD;

    /*ABRIR PRINCIPAL*/
    @GetMapping("/inicio")
    public String abrirPrincipal(@ModelAttribute Jugador jugador, HttpSession session, Model model) {

        String doc = (String) session.getAttribute("doc");
        Jugador jugadorAct  = repoJ.findByDocumento(doc);

        model.addAttribute("jugadorAct", jugadorAct);

        return "jugadores/principalJugadores";
    }

    @PostMapping("/registrar")
    public String grabarRegistroAtleta(@ModelAttribute Jugadores jugadores, Model model) {

        if(repoJ.findByDocumento(jugadores.getDocumento()) != null) {

            if(repoJug.findByDocumento(jugadores.getDocumento()) == null){
                try {
                    model.addAttribute("jugadores", new Jugadores());
                    jugadores.setFecreg(Fecha.fechaActual());
                    repoJug.save(jugadores);
                    model.addAttribute("mensaje", "Jugador Registrado");
                    model.addAttribute("valor", 1);
                } catch (Exception e) {
                    model.addAttribute("mensaje", "Error al Registrar");
                    model.addAttribute("valor", 2);
                }
            }else{
                model.addAttribute("mensaje", "El jugador ya se encuentra registrado");
                model.addAttribute("valor", 2);
            }
        }else {
            model.addAttribute("mensaje", "El jugador no está registrado en ningún equipo de este sistema");
            model.addAttribute("valor", 2);
        }

        return "atletas";
    }

    /*LOGUEAR*/
    @PostMapping("/validar")
    public String validarLogin(@ModelAttribute Jugadores jugadores, HttpSession session, Model model) {

        jugadores = repoJug.findByDocumentoAndClave(jugadores.getDocumento(), jugadores.getClave());
        System.out.println(jugadores);

        if (jugadores == null) {
            model.addAttribute("mensaje", "Documento o Clave Incorrecta");
            model.addAttribute("valor", 0);
            return "atletas";
        }
        session.setAttribute("doc", jugadores.getDocumento().toString());
        Jugador jugador = repoJ.findByDocumento((String)session.getAttribute("doc"));

        model.addAttribute("jugadorAct", jugador);


        return "jugadores/principalJugadores";
    }
    @GetMapping("/solicitud")
    public String abrirSolicitudes(@ModelAttribute Jugador jugador, HttpSession session, Model model, Jugadores jugadores) {

        String doc = (String) session.getAttribute("doc");
        jugador = repoJ.findByDocumento(doc);
        model.addAttribute("jugador", jugador);

        if(jugador.getCodestado() == 2){
            model.addAttribute("lstAgentes", repoA.findByCodjugadorAndCodestado(jugador.getCodjugador(),1));
        }else{
            model.addAttribute("lstAgentes", repoA.findByCodjugadorAndCodestado(jugador.getCodjugador(),2));
        }

        model.addAttribute("jugadorAct", jugador);
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

    /*ABRIR ESTADISTICAS*/
    @GetMapping("/stats")
    public String abrirEstadisticas(@ModelAttribute Jugador jugador,Eficiencia eficiencia, HttpSession session, Model model) {

        String doc = (String) session.getAttribute("doc");
        jugador = repoJ.findByDocumento(doc);
        model.addAttribute("jugador", jugador);

        List<Datos> lstDatos = repoD.findByCodjugador(jugador.getCodjugador());

        double el=0,ed=0,et=0,ec=0;

        for(int i=0;i<lstDatos.size();i++){
            el = el + (double)((double)lstDatos.get(i).getClibres()/(double)lstDatos.get(i).getIlibres());
            ed = ed +  (double)((double)lstDatos.get(i).getCdobles()/(double)lstDatos.get(i).getIdobles());
            et = et +  (double)((double)lstDatos.get(i).getCtriples()/(double)lstDatos.get(i).getItriples());
            ec = ec +  (double)((double)(lstDatos.get(i).getClibres()+lstDatos.get(i).getCdobles()+lstDatos.get(i).getCtriples())/(double)
                    (lstDatos.get(i).getIlibres()+lstDatos.get(i).getIdobles()+lstDatos.get(i).getItriples()));
        }

        DecimalFormat df = new DecimalFormat("#.00");

        eficiencia.setEfilibres(Double.parseDouble(df.format(el/lstDatos.size()*100)));
        eficiencia.setEfidobles(Double.parseDouble(df.format(ed/lstDatos.size()*100)));
        eficiencia.setEfitriples(Double.parseDouble(df.format(et/lstDatos.size()*100)));
        eficiencia.setEficampo(Double.parseDouble(df.format(ec/lstDatos.size()*100)));

        model.addAttribute("eficiencia", eficiencia);
        model.addAttribute("lstDatos", lstDatos);

        return "jugadores/stats";
    }
}
