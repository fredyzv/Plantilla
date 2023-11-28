package com.deporte.plantilla.controller;

import com.deporte.plantilla.model.Equipo;
import com.deporte.plantilla.model.Rol;
import com.deporte.plantilla.model.Usuario;
import com.deporte.plantilla.repository.IEquipoRepository;
import com.deporte.plantilla.repository.IUsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*CONTROLADOR INDIVIDUAL DEL USUARIO*/

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
	
	@Autowired
	private IUsuarioRepository repoU;
	@Autowired
	private IEquipoRepository repoE;
	
	/*ABRIR PRINCIPAL*/
	@GetMapping("/inicio")
	public String abrirPrincipal(@ModelAttribute Usuario usuario, HttpSession session, Model model) {

		String correo = (String) session.getAttribute("correo");
		
		Usuario usuarioAct = repoU.findByCorreo(correo);
		model.addAttribute("usuarioAct", usuarioAct);
		
		model.addAttribute("rol", session.getAttribute("rol"));

		return "principal";
	}
	
	/*LOGUEAR*/
	@PostMapping("/validar")
	public String validarLogin(@ModelAttribute Usuario usuarioAct, HttpSession session, Model model) {

		usuarioAct = repoU.findByCorreoAndClave(usuarioAct.getCorreo(), usuarioAct.getClave());
		try {
			if (usuarioAct == null) {
				model.addAttribute("mensaje", "Usuario o Clave Incorrecta");
				return "usuario";

			}

			if (usuarioAct.getCodestado() == 2) {
				model.addAttribute("mensaje", "Usuario Inactivo");
				return "usuario";
				//return "redirect:/socios";
			}

			model.addAttribute("usuarioAct", usuarioAct);

			session.setAttribute("correo", usuarioAct.getCorreo());
			session.setAttribute("rol", usuarioAct.getCodrol());

			model.addAttribute("rol", session.getAttribute("rol"));
			System.out.println("Entrando al try");
			return "principal";
		}catch (Exception e){
			e.printStackTrace();
			model.addAttribute("mensaje", "Usuario Inactivo");
			System.out.println("Entrando al catch");
			return "usuario";
		}


	}
	
	/* CARGAR VISTA DE PERFIL*/
	@GetMapping("/perfil")
	public String perfil(@ModelAttribute Rol rol, Usuario usuario, Equipo equipo, HttpSession session,
						 Model model) {
				
		String correo = (String) session.getAttribute("correo");
		
		model.addAttribute("rol", session.getAttribute("rol"));
		usuario = repoU.findByCorreo(correo);
		equipo = repoE.findByUsuarioAndCodestado(usuario.getUsuario(),1);

		model.addAttribute("usuario", usuario);
		model.addAttribute("equipo", equipo);
		
		return "perfil/perfil";
		}
	
	/*CARGAR ACTUALIZACION DE PERFIL*/
	@GetMapping("/perfilact")
	public String abrirActualizacionPerfil(@ModelAttribute Usuario usuario, HttpSession session, Model model) {

		String correo = (String) session.getAttribute("correo");
		
		model.addAttribute("rol", session.getAttribute("rol"));
		usuario = repoU.findByCorreo(correo);
		model.addAttribute("usuario", usuario);

		return "perfil/actperfil";
	}
	
	/*GRABAR ACTUALIZACION DE PERFIL*/
	@PostMapping("/perfilgra")
	public String actualizarPerfil(@ModelAttribute Usuario usuario, Model model) {
		
		  try {
		  
		  repoU.save(usuario); 
		  model.addAttribute("mensaje", "Usuario Actualizado");
		 
		  
		  } catch (Exception e) {
		  
		  model.addAttribute("mensaje", "Error al Actualizar");
		  
		  System.out.println("cambiar2 " + usuario);
		  }

		return "perfil/actperfil";
		  
	}
	
	

}
