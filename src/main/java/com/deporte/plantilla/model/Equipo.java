package com.deporte.plantilla.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="equipo")
@Data
public class Equipo {
	
	@Id
	private int codequipo;
	private String nomequipo;
	
	@ManyToOne
	@JoinColumn(name="usuario", insertable = false, updatable = false)
	private Usuario usu;
	private String usuario;

	private String dep;
	private String pro;
	private String dis;
		
	private String fecreg;
	
	@ManyToOne
	@JoinColumn(name="codliga", insertable = false, updatable = false)
	private Liga liga;
	private int codliga;

	@ManyToOne
	@JoinColumn(name = "codestado", insertable = false, updatable = false)
	private Estado estado;
	private int codestado;

}
