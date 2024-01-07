package com.deporte.plantilla.controller;

import com.deporte.plantilla.model.*;
import com.deporte.plantilla.repository.IEquipoRepository;
import com.deporte.plantilla.repository.IPartidoRepository;
import com.deporte.plantilla.repository.IUsuarioRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private IUsuarioRepository repoU;
    @Autowired
    private IEquipoRepository repoE;
    @Autowired
    private IPartidoRepository repoP;

    @GetMapping("/listar")
    public String abrirListadoStats(@ModelAttribute Usuario usuarioAct, DatosJugador datosJugador, Partido partido, Equipo equipo, Stats stats, HttpSession session,
                                    Model model) {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        if(usuarioAct.getCodrol()!=2){

            List<Equipo> lstEquipo = repoE.findAll();
            List<Stats> lstStat = new ArrayList<>();

            for(int p=0;p<lstEquipo.size();p++){

                List<Partido> lstPartidos = new ArrayList<>();
                lstPartidos = repoP.findByCodequipo(lstEquipo.get(p).getCodequipo());
                String nom = lstEquipo.get(p).getNomequipo();
                stats.setNombreEquipo(nom);
                int sl=0,sv=0,c1l=0,c2l=0,c3l=0,c4l=0,c1v=0,c2v=0,c3v=0,c4v=0,s1l=0,s1v=0,s2l=0,s2v=0,s3l=0,s3v=0;
                DecimalFormat df = new DecimalFormat("#.00");

                for(int i=0;i<lstPartidos.size();i++){

                    stats.setScoreLocal(Double.parseDouble(df.format((double)(sl = sl + lstPartidos.get(i).getScoreequipo())/lstPartidos.size())));
                    stats.setScoreVisita(Double.parseDouble(df.format((double)(sv = sv + lstPartidos.get(i).getScorerival())/lstPartidos.size())));
                    stats.setC1l(Double.parseDouble(df.format((double)(c1l = c1l + lstPartidos.get(i).getPcequipo())/lstPartidos.size())));
                    stats.setC2l(Double.parseDouble(df.format((double)(c2l = c2l + lstPartidos.get(i).getScequipo())/lstPartidos.size())));
                    stats.setC3l(Double.parseDouble(df.format((double)(c3l = c3l + lstPartidos.get(i).getTcequipo())/lstPartidos.size())));
                    stats.setC4l(Double.parseDouble(df.format((double)(c4l = c4l + lstPartidos.get(i).getCcequipo())/lstPartidos.size())));
                    stats.setS1l(Double.parseDouble(df.format((double)(s1l = s1l + lstPartidos.get(i).getPsequipo())/lstPartidos.size())));
                    stats.setS2l(Double.parseDouble(df.format((double)(s2l = s2l + lstPartidos.get(i).getSsequipo())/lstPartidos.size())));
                    stats.setS3l(Double.parseDouble(df.format((double)(s3l = s3l + lstPartidos.get(i).getTsequipo())/lstPartidos.size())));
                    stats.setC1v(Double.parseDouble(df.format((double)(c1v = c1v + lstPartidos.get(i).getPcrival())/lstPartidos.size())));
                    stats.setC2v(Double.parseDouble(df.format((double)(c2v = c2v + lstPartidos.get(i).getScrival())/lstPartidos.size())));
                    stats.setC3v(Double.parseDouble(df.format((double)(c3v = c3v + lstPartidos.get(i).getTcrival())/lstPartidos.size())));
                    stats.setC4v(Double.parseDouble(df.format((double)(c4v = c4v + lstPartidos.get(i).getCcrival())/lstPartidos.size())));
                    stats.setS1v(Double.parseDouble(df.format((double)(s1v = s1v + lstPartidos.get(i).getPsrival())/lstPartidos.size())));
                    stats.setS2v(Double.parseDouble(df.format((double)(s2v = s2v + lstPartidos.get(i).getSsrival())/lstPartidos.size())));
                    stats.setS3v(Double.parseDouble(df.format((double)(s3v = s3v + lstPartidos.get(i).getTsrival())/lstPartidos.size())));
                }
                lstStat.add(stats);
                stats = new Stats();
                lstPartidos.clear();
            }

            model.addAttribute("lstStats", lstStat);
        }else{
            equipo = repoE.findByUsuarioAndCodestado(usuarioAct.getUsuario(),1);
            model.addAttribute("equipo", equipo);

            if(equipo == null){
                model.addAttribute("mensaje", "Primero debes registrar un equipo");
                model.addAttribute("mostrar", "no");
                return "equipo/equipo";

            }else {
                List<Partido> lstPartidos = repoP.findByCodequipo(equipo.getCodequipo());
                stats.setNombreEquipo(equipo.getNomequipo());
                if (lstPartidos.isEmpty()) {
                    model.addAttribute("mensaje", "Aún no Existen partidos registrados");
                } else {
                    int sl = 0, sv = 0, c1l = 0, c2l = 0, c3l = 0, c4l = 0, c1v = 0, c2v = 0, c3v = 0, c4v = 0, s1l = 0, s1v = 0, s2l = 0, s2v = 0, s3l = 0, s3v = 0;

                    DecimalFormat df = new DecimalFormat("#.00");
                    for (int i = 0; i < lstPartidos.size(); i++) {
                        stats.setScoreLocal(Double.parseDouble(df.format((double) (sl = sl + lstPartidos.get(i).getScoreequipo()) / lstPartidos.size())));
                        stats.setScoreVisita(Double.parseDouble(df.format((double) (sv = sv + lstPartidos.get(i).getScorerival()) / lstPartidos.size())));
                        stats.setC1l(Double.parseDouble(df.format((double) (c1l = c1l + lstPartidos.get(i).getPcequipo()) / lstPartidos.size())));
                        stats.setC2l(Double.parseDouble(df.format((double) (c2l = c2l + lstPartidos.get(i).getScequipo()) / lstPartidos.size())));
                        stats.setC3l(Double.parseDouble(df.format((double) (c3l = c3l + lstPartidos.get(i).getTcequipo()) / lstPartidos.size())));
                        stats.setC4l(Double.parseDouble(df.format((double) (c4l = c4l + lstPartidos.get(i).getCcequipo()) / lstPartidos.size())));
                        stats.setS1l(Double.parseDouble(df.format((double) (s1l = s1l + lstPartidos.get(i).getPsequipo()) / lstPartidos.size())));
                        stats.setS2l(Double.parseDouble(df.format((double) (s2l = s2l + lstPartidos.get(i).getSsequipo()) / lstPartidos.size())));
                        stats.setS3l(Double.parseDouble(df.format((double) (s3l = s3l + lstPartidos.get(i).getTsequipo()) / lstPartidos.size())));
                        stats.setC1v(Double.parseDouble(df.format((double) (c1v = c1v + lstPartidos.get(i).getPcrival()) / lstPartidos.size())));
                        stats.setC2v(Double.parseDouble(df.format((double) (c2v = c2v + lstPartidos.get(i).getScrival()) / lstPartidos.size())));
                        stats.setC3v(Double.parseDouble(df.format((double) (c3v = c3v + lstPartidos.get(i).getTcrival()) / lstPartidos.size())));
                        stats.setC4v(Double.parseDouble(df.format((double) (c4v = c4v + lstPartidos.get(i).getCcrival()) / lstPartidos.size())));
                        stats.setS1v(Double.parseDouble(df.format((double) (s1v = s1v + lstPartidos.get(i).getPsrival()) / lstPartidos.size())));
                        stats.setS2v(Double.parseDouble(df.format((double) (s2v = s2v + lstPartidos.get(i).getSsrival()) / lstPartidos.size())));
                        stats.setS3v(Double.parseDouble(df.format((double) (s3v = s3v + lstPartidos.get(i).getTsrival()) / lstPartidos.size())));
                    }
                    model.addAttribute("stats", stats);
                }
            }
        }

        return "stats/stats";
    }
}
