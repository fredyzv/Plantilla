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
    private DatosService serviceDatos;
    @Autowired
    private IDatosRepository repoD;


    /* ABRIR PAGINA DE PARTIDOS */
    @GetMapping("/listado")
    public String abrirListadoPartidos(@ModelAttribute Usuario usuarioAct, Equipo equipo, Partido partido, HttpSession session,
                                       Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        if (usuarioAct.getCodrol()==2) {
            equipo = repoE.findByUsuarioAndCodestado(usuarioAct.getUsuario(), 1);
            model.addAttribute("equipo", equipo);
        }
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
        String url=null;

        if (usuarioAct.getCodrol()==2){
            equipo = repoE.findByUsuarioAndCodestado(usuarioAct.getUsuario(),1);
        }

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
        if (usuarioAct.getCodrol()==2) {
            equipo = repoE.findByUsuarioAndCodestado(usuarioAct.getUsuario(), 1);
        model.addAttribute("equipo", equipo);
        }
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
        if (usuarioAct.getCodrol()==2) {
            equipo = repoE.findByUsuarioAndCodestado(usuarioAct.getUsuario(), 1);
        }
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

        List<Jugador> listJ = repoJ.findByCodcategoriaAndCodequipo(partido.getCodcategoria(), partido.getCodequipo());

        datosJugador.setListJugador(listJ);

        model.addAttribute("datosJugador", datosJugador);
        model.addAttribute("equipo", equipo);
        model.addAttribute("partido", partido);
        model.addAttribute("id", id);
        session.setAttribute("id", id);

        return "partido/datos";
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
        List<Jugador> jugadores = repoJ.findByCodcategoriaAndCodequipo(partido.getCodcategoria(), partido.getCodequipo());

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

}
