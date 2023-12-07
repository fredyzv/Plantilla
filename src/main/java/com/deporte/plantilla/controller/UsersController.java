package com.deporte.plantilla.controller;

import com.deporte.plantilla.model.Usuario;
import com.deporte.plantilla.repository.*;
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
@RequestMapping("/user")
public class UsersController {

	@Autowired
	private IUsuarioRepository repoU;
	@Autowired
	private IEstadoRepository repoE;
	@Autowired
	private IDocumentoRepository repoD;
	@Autowired
	private INivelRepository repoN;
	@Autowired
	private ISexoRepository repoS;
	@Autowired
	private IRolRepository repoR;

	/* ABRIR LISTA DE USUARIOS */
	@GetMapping("/usuario")
	public String abrirListadoUsuarios(@ModelAttribute Usuario usuarioAct, Usuario usuario, HttpSession session,
									   Model model) {

		String correo = (String) session.getAttribute("correo");
		model.addAttribute("rol", session.getAttribute("rol"));
		usuarioAct = repoU.findByCorreo(correo);
		model.addAttribute("usuarioAct", usuarioAct);
		model.addAttribute("usuario", usuario);

		model.addAttribute("lstUsuario", repoU.findAll());

		return "usuarios/usuarios";
	}

	/* NUEVO USUARIO */
	@GetMapping("/nuevo")
	public String abrirNuevoUsuario(@ModelAttribute Usuario usuarioAct, Usuario usuario, HttpSession session,
			Model model) {

		String correo = (String) session.getAttribute("correo");
		model.addAttribute("rol", session.getAttribute("rol"));
		usuarioAct = repoU.findByCorreo(correo);
		model.addAttribute("usuarioAct", usuarioAct);

		model.addAttribute("lstUsuario", repoU.findAll());
		model.addAttribute("lstEstado", repoE.findAll());
		model.addAttribute("lstNivel", repoN.findAll());
		model.addAttribute("lstDocumento", repoD.findAll());
		model.addAttribute("lstSexo", repoS.findAll());

		return "usuarios/nuevo";
	}

	/* GRABAR NUEVO USUARIO */
	@PostMapping("/grabar")
	public String grabarRegistro(@ModelAttribute Usuario usuarioAct, Usuario usuario, HttpSession session,
			Model model) {

		String correo = (String) session.getAttribute("correo");
		model.addAttribute("rol", session.getAttribute("rol"));
		usuarioAct = repoU.findByCorreo(correo);
		model.addAttribute("usuarioAct", usuarioAct);

		model.addAttribute("usuario", usuario);

		try {

			usuario.setUsuario(usuario.getNombres().substring(0, 1) + usuario.getApellidos().substring(0, 1)
					+ usuario.getDocumento());
			usuario.setCodrol(2);
			usuario.setCodestado(1);
			usuario.setFecreg(Fecha.fechaActual());

			repoU.save(usuario);
			model.addAttribute("mensaje", "Usuario Registrado");

		} catch (Exception e) {

			model.addAttribute("mensaje", "Error al Registrar");
			System.out.println("Error " + e);
		}

		model.addAttribute("lstUsuario", repoU.findAll());
		model.addAttribute("lstEstado", repoE.findAll());
		model.addAttribute("lstNivel", repoN.findAll());
		model.addAttribute("lstDocumento", repoD.findAll());
		model.addAttribute("lstSexo", repoS.findAll());

		System.out.println("Enviado " + usuario);
		return "usuarios/nuevo";
	}

	/* ACTUALIZAR USUARIO */
	@PostMapping("/actualizar")
	public String abrirActualizacionUsuario(@ModelAttribute Usuario usuario, HttpSession session, Model model) {

		String correo = (String) session.getAttribute("correo");
		model.addAttribute("rol", session.getAttribute("rol"));
		Usuario usuarioAct = repoU.findByCorreo(correo);
		model.addAttribute("usuarioAct", usuarioAct);
		model.addAttribute("usuario", usuario);
		model.addAttribute("lstUsuario", repoU.findAll());
		model.addAttribute("lstEstado", repoE.findAll());
		model.addAttribute("lstNivel", repoN.findAll());
		model.addAttribute("lstDocumento", repoD.findAll());
		model.addAttribute("lstSexo", repoS.findAll());
		model.addAttribute("lstRol", repoR.findAll());

		return "usuarios/actualizar";
	}

	@PostMapping("/actualizado")
	public String ActualizacionUsuario(@ModelAttribute Usuario usuario, HttpSession session, Model model) {

		String correo = (String) session.getAttribute("correo");
		model.addAttribute("rol", session.getAttribute("rol"));
		Usuario usuarioAct = repoU.findByCorreo(correo);
		model.addAttribute("usuarioAct", usuarioAct);

		System.out.println("usuario " + usuario);

		try {

			// usuario.setFecreg(Fecha.fechaActual());

			repoU.save(usuario);
			model.addAttribute("mensaje", "Usuario Actualizado");

		} catch (Exception e) {

			model.addAttribute("mensaje", "Error al Registrar");

		}

		model.addAttribute("lstUsuario", repoU.findAll());
		model.addAttribute("lstEstado", repoE.findAll());
		model.addAttribute("lstNivel", repoN.findAll());
		model.addAttribute("lstDocumento", repoD.findAll());
		model.addAttribute("lstSexo", repoS.findAll());
		model.addAttribute("lstRol", repoR.findAll());

		System.out.println("usuario 2 " + usuario);

		return "usuarios/actualizar";
	}
	
	/*ELIMINAR USUARIO*/
	@PostMapping("/eliminar")
	public String EliminarUsuario(@ModelAttribute Usuario usuario, HttpSession session, Model model) {

		String correo = (String) session.getAttribute("correo");
		model.addAttribute("rol", session.getAttribute("rol"));
		Usuario usuarioAct = repoU.findByCorreo(correo);
		model.addAttribute("usuarioAct", usuarioAct);
		model.addAttribute("lstUsuario", repoU.findAll());

		try {
			usuario.setCodestado(2);
			repoU.save(usuario);
			model.addAttribute("mensaje", "Usuario Inactivo");
			model.addAttribute("lstUsuario", repoU.findAll());

		} catch (Exception e) {

			model.addAttribute("mensaje", "Error al Eliminar");
			model.addAttribute("lstUsuario", repoU.findAll());

		}
		
		model.addAttribute("lstUsuario", repoU.findAll());

		return "redirect:/user/usuario";
	}
	
	/* VER USUARIO */
	@PostMapping("/ver")
	public String abrirVerUsuario(@ModelAttribute Usuario usuario, HttpSession session, Model model) {

		String correo = (String) session.getAttribute("correo");

		model.addAttribute("rol", session.getAttribute("rol"));

		Usuario usuarioAct = repoU.findByCorreo(correo);
		model.addAttribute("usuarioAct", usuarioAct);

		model.addAttribute("lstUsuario", repoU.findAll());
		model.addAttribute("lstEstado", repoE.findAll());
		model.addAttribute("lstNivel", repoN.findAll());
		model.addAttribute("lstDocumento", repoD.findAll());
		model.addAttribute("lstSexo", repoS.findAll());

		return "usuarios/ver";
	}

}
