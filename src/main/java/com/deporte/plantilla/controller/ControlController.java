package com.deporte.plantilla.controller;

import com.deporte.plantilla.model.Control;
import com.deporte.plantilla.model.Usuario;
import com.deporte.plantilla.repository.IControlRepository;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/control")
public class ControlController {

    @Autowired
    private IUsuarioRepository repoU;
    @Autowired
    private IControlRepository repoC;

    /* ABRIR PAGOS */
    @GetMapping("/listar")
    public String abrirConsultas(@ModelAttribute Usuario usuarioAct, Control control, HttpSession session, Model model) throws ParseException {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        List<Control> listControl = repoC.findAll();
        for(int i=0;i<listControl.size();i++){
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            Date inicio = date.parse(listControl.get(i).getFecact());
            Date actual = date.parse(Fecha.fechaActualSinHora());
            long meses_transcurridos = actual.getTime()-inicio.getTime();
            TimeUnit unidad = TimeUnit.DAYS;
            long dias = unidad.convert(meses_transcurridos,TimeUnit.MILLISECONDS);
            double mes = dias/30;
            listControl.get(i).setMeses((int)mes);
            repoC.save(listControl.get(i));
        }

        model.addAttribute("lstControl", repoC.findAll());

        return "control/listar";
    }
    /*REALIZAR PAGO DE USUARIO*/
    @PostMapping("/pagar")
    public String Pagar(@ModelAttribute Usuario usuario, Control control, HttpSession session, Model model )throws ParseException {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        Usuario usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        try {
            control = repoC.findByUsuario(control.getUsuario());
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            Date actual = date.parse(control.getFecact());
            actual.setMonth(actual.getMonth()+1);
            String fecha = date.format(actual);
            control.setFecact(fecha);
            repoC.save(control);
            model.addAttribute("mensaje", "Pago Realizado");
        }catch (Exception e){
            model.addAttribute("mensaje", "Error al pagar");
        }

        return "redirect:/control/listar";
    }

    /*SUSPENDER USUARIO*/
    @PostMapping("/eliminar")
    public String EliminarUsuario(@ModelAttribute Usuario usuario, Control control, HttpSession session, Model model )throws ParseException {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        Usuario usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        usuario = repoU.findById(control.getUsuario()).get();

        try {
            usuario.setCodestado(2);
            repoU.save(usuario);
            model.addAttribute("mensaje", "Usuario Inactivo");
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al Eliminar");
        }

        model.addAttribute("lstControl", repoC.findAll());

        return "redirect:/control/listar";
    }

    /*ACTIVAR USUARIO*/
    @PostMapping("/activar")
    public String ActivarUsuario(@ModelAttribute Usuario usuario, Control control, HttpSession session, Model model )throws ParseException {

        String correo = (String) session.getAttribute("correo");
        model.addAttribute("rol", session.getAttribute("rol"));
        Usuario usuarioAct = repoU.findByCorreo(correo);
        model.addAttribute("usuarioAct", usuarioAct);

        usuario = repoU.findById(control.getUsuario()).get();

        try {
            usuario.setCodestado(1);
            repoU.save(usuario);
            model.addAttribute("mensaje", "Usuario activado");
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al activar");
        }

        model.addAttribute("lstControl", repoC.findAll());

        return "redirect:/control/listar";
    }



}
