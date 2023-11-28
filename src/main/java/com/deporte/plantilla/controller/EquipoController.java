package com.deporte.plantilla.controller;



import com.deporte.plantilla.model.Equipo;
import com.deporte.plantilla.model.Liga;
import com.deporte.plantilla.model.Ubigeo;
import com.deporte.plantilla.model.Usuario;
import com.deporte.plantilla.repository.*;
import com.deporte.plantilla.service.UbigeoService;
import com.deporte.plantilla.util.Fecha;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/equipo")
public class EquipoController {
	
	@Autowired
	private IUsuarioRepository repoU;
	@Autowired
	private IEquipoRepository repoE;
	@Autowired
	private ILigaRepository repoL;
	@Autowired
	private IUbigeoRepository repoUbi;
	@Autowired
	private IEstadoRepository repoEs;
	@Autowired
	private UbigeoService servicio;

	@RequestMapping("/listaDepartamentos")
	@ResponseBody
	public List<String> listaDep(){
		return servicio.traeDepartamentos();
	}

	@RequestMapping("/listaProvincias")
	@ResponseBody
	public List<String> listaProv(Ubigeo ubigeo){
		return servicio.traeProvincias(ubigeo);
	}

	@RequestMapping("/listaDistritos")
	@ResponseBody
	public List<String> listaDistritos(Ubigeo ubigeo){
		return servicio.traeDistritos(ubigeo);
	}
	
	/*CARGRA VISTA DE EQUIPO*/
	@GetMapping("/equipo")
	public String abrirEquipo(@ModelAttribute Usuario usuario, Usuario usuarioAct, @ModelAttribute Equipo equipo, Ubigeo ubigeo, HttpSession session, Model model) {

		String correo = (String) session.getAttribute("correo");
		
		model.addAttribute("rol", session.getAttribute("rol"));
		usuarioAct = repoU.findByCorreo(correo);
		model.addAttribute("usuarioAct", usuarioAct);

		if(usuarioAct.getCodrol() != 2){
			model.addAttribute("lstEquipo", repoE.findAll());
			model.addAttribute("mostrar", "no");
		}else{
			equipo = repoE.findByUsuarioAndCodestado(usuarioAct.getUsuario(), 1);

			if(equipo != null){
				model.addAttribute("lstEquipo", equipo);
			}else{
				model.addAttribute("mostrar", "si");
			}

		}
		return "equipo/equipo";
	}
	
	/* NUEVO EQUIPO */
	@GetMapping("/nuevo")
	public String abrirNuevoEquipo(@ModelAttribute Usuario usuarioAct, Equipo equipo, HttpSession session, Model model) {

		String correo = (String) session.getAttribute("correo");
		model.addAttribute("rol", session.getAttribute("rol"));
		usuarioAct = repoU.findByCorreo(correo);
		model.addAttribute("usuarioAct", usuarioAct);

		model.addAttribute("equipo", equipo);
		model.addAttribute("lstLiga", repoL.findByCodestado(1));

		return "equipo/nuevo";
	}

	@PostMapping("/grabar")
	public String grabarRegistro(@ModelAttribute Usuario usuarioAct, Usuario usuario, Liga liga, HttpSession session, Equipo equipo, Model model) {

		String correo = (String) session.getAttribute("correo");
		model.addAttribute("rol", session.getAttribute("rol"));
		usuarioAct = repoU.findByCorreo(correo);
		model.addAttribute("usuarioAct", usuarioAct);

		model.addAttribute("equipo", equipo);

		try {
			equipo.setUsuario(usuarioAct.getUsuario());
			equipo.setFecreg(Fecha.fechaActual());
			equipo.setCodestado(1);

			repoE.save(equipo);
			model.addAttribute("mensaje", "Equipo Registrado");

		} catch (Exception e) {

			model.addAttribute("mensaje", "Error al Registrar");

		}

		model.addAttribute("lstLiga", repoL.findByCodestado(1));

		return "redirect:/equipo/equipo";
	}

	/*ACTUALIZAR EQUIPO*/
	@PostMapping("/actualizar")
	public String abrirActualizacionEquipo(@ModelAttribute Usuario usuarioAct, HttpSession session, Equipo equipo, Model model) {

		String correo = (String) session.getAttribute("correo");
		model.addAttribute("rol", session.getAttribute("rol"));
		usuarioAct = repoU.findByCorreo(correo);
		model.addAttribute("usuarioAct", usuarioAct);

		equipo = repoE.findById(equipo.getCodequipo()).get();

		model.addAttribute("equipo", equipo);
		model.addAttribute("lstLiga", repoL.findByCodestado(1));
		model.addAttribute("lstEstado", repoEs.findAll());

		return "equipo/actualizar";
	}

	@PostMapping("/actualizado")
	public String ActualizacionEquipo(@ModelAttribute Usuario usuarioAct,Equipo equipo, Liga liga, HttpSession session, Model model) {

		String correo = (String) session.getAttribute("correo");
		model.addAttribute("rol", session.getAttribute("rol"));
		usuarioAct = repoU.findByCorreo(correo);

		try {

			repoE.save(equipo);
			model.addAttribute("mensaje", "Equipo Actualizado");

		} catch (Exception e) {

			model.addAttribute("mensaje", "Error al Actualizar");

		}

		model.addAttribute("lstLiga", repoL.findByCodestado(1));
		model.addAttribute("equipo", equipo);
		model.addAttribute("lstEstado", repoEs.findAll());

		return "redirect:/equipo/equipo";
	}

	/*ELIMINAR EQUIPO*/
	@PostMapping("/eliminar")
	public String EliminarEquipo(@ModelAttribute Usuario usuario, Equipo equipo, HttpSession session, Model model) {

		String correo = (String) session.getAttribute("correo");
		model.addAttribute("rol", session.getAttribute("rol"));
		Usuario usuarioAct = repoU.findByCorreo(correo);
		model.addAttribute("usuarioAct", usuarioAct);
		model.addAttribute("lstUsuario", repoU.findAll());

		try {
			equipo = repoE.findById(equipo.getCodequipo()).get();
			equipo.setCodestado(2);
			repoE.save(equipo);
			model.addAttribute("mensaje", "Equipo Inactivo");

		} catch (Exception e) {

			model.addAttribute("mensaje", "Error al Eliminar");
		}

		return "redirect:/equipo/equipo";
	}

	/* VER EQUIPO */
	@PostMapping("/ver")
	public String abrirVerEquipo(@ModelAttribute Usuario usuario, Equipo equipo, HttpSession session, Model model) {

		String correo = (String) session.getAttribute("correo");
		model.addAttribute("rol", session.getAttribute("rol"));
		Usuario usuarioAct = repoU.findByCorreo(correo);
		model.addAttribute("usuarioAct", usuarioAct);

		equipo = repoE.findById(equipo.getCodequipo()).get();

		model.addAttribute("equipo", equipo);

		return "equipo/ver";
	}

}
