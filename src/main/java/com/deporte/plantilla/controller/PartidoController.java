package com.deporte.plantilla.controller;

import com.deporte.plantilla.model.*;
import com.deporte.plantilla.repository.*;
import com.deporte.plantilla.service.DatosService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


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
    @Autowired
    private IDatosRepository repoD;
    @Autowired
    private DatosService serviceDatos;


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
            if(equipo == null){
                model.addAttribute("mensaje", "Primero debes registrar un equipo");
                model.addAttribute("mostrar", "no");
                return "equipo/equipo";

            }else {
                model.addAttribute("lstPartido", repoP.findByCodequipo(equipo.getCodequipo()));
            }
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

        if(equipo == null){
            model.addAttribute("lstEquipo", repoE.findAll());
            model.addAttribute("mostrar", "no");
            return "equipo/equipo";
        }

        model.addAttribute("equipo", equipo);
        model.addAttribute("partido", partido);
        model.addAttribute("lstTemporada", repoT.findByCodestado(1));
        model.addAttribute("lstCategoria", repoC.findByCodestado(1));
        model.addAttribute("lstLiga", repoL.findByCodestado(1));

        return "partido/nuevo";
    }

    @PostMapping("/grabar")
    public String grabarRegistro(@ModelAttribute Usuario usuarioAct, Equipo equipo, Partido partido, HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        equipo = repoE.findByUsuarioAndCodestado(usuarioAct.getUsuario(),1);
        model.addAttribute("equipo", equipo);

        if((partido.getPcequipo()+partido.getScequipo()+partido.getTcequipo()+partido.getCcequipo()+partido.getPsequipo()+partido.getSsequipo()+partido.getTsequipo())
                != partido.getScoreequipo()){
            model.addAttribute("mensaje", "Algunos datos por cuarto no coinciden con el Score Total del equipo");
        }else{
            if((partido.getPcrival()+partido.getScrival()+partido.getTcrival()+partido.getCcrival()+partido.getPsrival()+partido.getSsrival()+partido.getTsrival())
                    != partido.getScorerival()){
                model.addAttribute("mensaje", "Algunos datos por cuarto no coinciden con el Score Total del rival");
            }else{
                try {
                    partido.setCodequipo(equipo.getCodequipo());
                    repoP.save(partido);
                    model.addAttribute("mensaje", "Partido Registrado");
                } catch (Exception e) {
                    model.addAttribute("mensaje", "Error al Registrar");
                }
            }
        }

        model.addAttribute("partido", partido);
        model.addAttribute("lstTemporada", repoT.findByCodestado(1));
        model.addAttribute("lstCategoria", repoC.findByCodestado(1));
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
        model.addAttribute("lstTemporada", repoT.findByCodestado(1));
        model.addAttribute("lstCategoria", repoC.findByCodestado(1));
        model.addAttribute("lstLiga", repoL.findByCodestado(1));

        return "partido/actualizar";
    }

    @PostMapping("/actualizado")
    public String ActualizacionEquipo(@ModelAttribute Usuario usuarioAct, Equipo equipo, Partido partido, HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        if((partido.getPcequipo()+partido.getScequipo()+partido.getTcequipo()+partido.getCcequipo()+partido.getPsequipo()+partido.getSsequipo()+partido.getTsequipo())
                != partido.getScoreequipo()){
            model.addAttribute("mensaje", "Algunos datos por cuarto no coinciden con el Score Total del equipo");
        }else{
            if((partido.getPcrival()+partido.getScrival()+partido.getTcrival()+partido.getCcrival()+partido.getPsrival()+partido.getSsrival()+partido.getTsrival())
                    != partido.getScorerival()){
                model.addAttribute("mensaje", "Algunos datos por cuarto no coinciden con el Score Total del rival");
            }else{
                try {
                    repoP.save(partido);
                    model.addAttribute("mensaje", "Partido Actualizado");
                } catch (Exception e) {
                    model.addAttribute("mensaje", "Error al Actualizar");
                }
            }
        }

        model.addAttribute("partido", partido);
        model.addAttribute("lstTemporada", repoT.findByCodestado(1));
        model.addAttribute("lstCategoria", repoC.findByCodestado(1));
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
        if (usuarioAct.getCodrol()==2) {
            equipo = repoE.findById(partido.getCodequipo()).get();
        }
        model.addAttribute("equipo", equipo);
        model.addAttribute("partido", partido);

        return "partido/ver";
    }

    /*REGISTRAR DATOS*/
    @GetMapping("/datos/{id}")
    public String abrirDatosPartido(@ModelAttribute Usuario usuarioAct,DatosJugador datosJugador, Equipo equipo, Jugador jugador,
                                    Partido partido, Datos datos, HttpSession session, Model model,
                                    @PathVariable("id") int id) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        equipo = repoE.findByUsuarioAndCodestado(usuarioAct.getUsuario(),1);
        partido = repoP.findById(id).get();
        String url = null;

        if (!repoD.findByCodpartido(partido.getCodpartido()).isEmpty()){
            model.addAttribute("mensaje","Este partido ya tiene datos registrados");
            model.addAttribute("equipo", equipo);
            model.addAttribute("lstPartido", repoP.findByCodequipo(equipo.getCodequipo()));
            url= "partido/listado";
        }else {
            List<Jugador> listJ = repoJ.findByCodcategoriaAndCodequipoAndCodestado(partido.getCodcategoria(), partido.getCodequipo(),1);
            datosJugador.setListJugador(listJ);
            model.addAttribute("datosJugador", datosJugador);
            model.addAttribute("equipo", equipo);
            model.addAttribute("partido", partido);
            model.addAttribute("id", id);
            session.setAttribute("id", id);
            url= "partido/datos";
        }

        return url;
    }

    @PostMapping("/datosGrabar")
    public ModelAndView grabarDatosPartido(@ModelAttribute Usuario usuarioAct, Equipo equipo, Partido partido, Datos datos, HttpSession session, Model model
            , DatosJugador datosJugador) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        List<Datos> listadoDatos = new ArrayList();
        int cod = (Integer) session.getAttribute("id");
        partido = repoP.findById(cod).get();
        List<Jugador> jugadores = repoJ.findByCodcategoriaAndCodequipoAndCodestado(partido.getCodcategoria(), partido.getCodequipo(),1);

        for(int i = 0; i < jugadores.size(); i++) {
            datos.setCodjugador(jugadores.get(i).getCodjugador());
            datos.setCodpartido(cod);
            datos.setIlibres(datosJugador.getListJugador().get(i).getDatos().getIlibres());
            datos.setClibres(datosJugador.getListJugador().get(i).getDatos().getClibres());
            datos.setIdobles(datosJugador.getListJugador().get(i).getDatos().getIdobles());
            datos.setCdobles(datosJugador.getListJugador().get(i).getDatos().getCdobles());
            datos.setItriples(datosJugador.getListJugador().get(i).getDatos().getItriples());
            datos.setCtriples(datosJugador.getListJugador().get(i).getDatos().getCtriples());
            datos.setRebdef(datosJugador.getListJugador().get(i).getDatos().getRebdef());
            datos.setRebofe(datosJugador.getListJugador().get(i).getDatos().getRebofe());
            datos.setAsist(datosJugador.getListJugador().get(i).getDatos().getAsist());
            datos.setRecup(datosJugador.getListJugador().get(i).getDatos().getRecup());
            datos.setPerd(datosJugador.getListJugador().get(i).getDatos().getPerd());
            datos.setTapfav(datosJugador.getListJugador().get(i).getDatos().getTapfav());
            datos.setTapcont(datosJugador.getListJugador().get(i).getDatos().getTapcont());
            datos.setFaltdadas(datosJugador.getListJugador().get(i).getDatos().getFaltdadas());
            datos.setFaltasrec(datosJugador.getListJugador().get(i).getDatos().getFaltasrec());
            datos.setTiempo(datosJugador.getListJugador().get(i).getDatos().getTiempo());
            listadoDatos.add(datos);
            datos = new Datos();
        }

        try {
            serviceDatos.insertaRegistros(listadoDatos);

        }catch (Exception e){
            model.addAttribute("mensaje","error al grabar");
        }

        ModelAndView modelAndView = new ModelAndView("redirect:/partido/datos/" + session.getAttribute("id"));
        model.addAttribute("mensaje", "datos grabados");
        return modelAndView;
    }

    /*VER DATOS*/
    @PostMapping("/verdatos")
    public String abrirVerDatosPartido(@ModelAttribute Usuario usuarioAct, Equipo equipo, Partido partido, HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        partido = repoP.findById(partido.getCodpartido()).get();
        equipo = repoE.findById(partido.getCodequipo()).get();

        model.addAttribute("partido", partido);
        model.addAttribute("equipo", equipo);

        List<Datos> lstDatos = repoD.findByCodpartido(partido.getCodpartido());
        System.out.println(lstDatos);

        if(lstDatos.isEmpty()){
            model.addAttribute("mensaje","No Existen Datos para este Partido");
        }else{
            model.addAttribute("lstDatos", lstDatos);
        }

        return "partido/verDatos";
    }

    /* ACTUALIZAR DATOS PARTIDO */
    @PostMapping("/editar")
    public String abrirActualizacionDatos(@ModelAttribute Usuario usuarioAct,Jugador jugador, HttpSession session,Datos datos, Partido partido, Equipo equipo, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        partido = repoP.findById((Integer) session.getAttribute("id")).get();
        equipo = repoE.findById(partido.getCodequipo()).get();
        datos = repoD.findById(datos.getCoddatos()).get();
        jugador = repoJ.findById(datos.getCodjugador()).get();

        model.addAttribute("datos", datos);
        model.addAttribute("jugador", jugador);
        model.addAttribute("lstJugador", repoJ.findByCodcategoriaAndCodequipoAndCodestado(partido.getCodcategoria(), equipo.getCodequipo(), 1));
        model.addAttribute("partido", partido);
        model.addAttribute("equipo", equipo);
        model.addAttribute("id", session.getAttribute("id"));

        return "partido/editardatos";
    }

    @PostMapping("/editado")
    public String ActualizacionDatos(@ModelAttribute Usuario usuarioAct,Jugador jugador, HttpSession session,Datos datos, Partido partido, Equipo equipo, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        try {
            repoD.save(datos);
            model.addAttribute("mensaje", "Datos Actualizados");

            partido = repoP.findById((Integer) session.getAttribute("id")).get();
            equipo = repoE.findById(partido.getCodequipo()).get();
            datos = repoD.findById(datos.getCoddatos()).get();
            jugador = repoJ.findById(datos.getCodjugador()).get();

            model.addAttribute("datos", datos);
            model.addAttribute("jugador", jugador);
            model.addAttribute("lstJugador", repoJ.findByCodcategoriaAndCodequipoAndCodestado(partido.getCodcategoria(), equipo.getCodequipo(), 1));
            model.addAttribute("partido", partido);
            model.addAttribute("equipo", equipo);
            model.addAttribute("id", session.getAttribute("id"));
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al Registrar");

        }

        return "partido/editardatos";
    }

    /* ELIMINAR DATOS PARTIDO */
    @PostMapping("/eliminar")
    public String eliminarDatos(@ModelAttribute Usuario usuarioAct,Jugador jugador, HttpSession session,Datos datos, Partido partido, Equipo equipo, Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        try {
            datos = repoD.findById(datos.getCoddatos()).get();
            repoD.delete(datos);

            partido = repoP.findById(datos.getCodpartido()).get();
            equipo = repoE.findById(partido.getCodequipo()).get();
            model.addAttribute("partido", partido);
            model.addAttribute("equipo", equipo);
            model.addAttribute("datos", datos);
            List<Datos> lstDatos = repoD.findByCodpartido(partido.getCodpartido());
            model.addAttribute("lstDatos", lstDatos);
            model.addAttribute("mensaje","Registro eliminado con éxito");
        }catch (Exception e){
            e.printStackTrace();
        }

        return "partido/verdatos";
    }

}
