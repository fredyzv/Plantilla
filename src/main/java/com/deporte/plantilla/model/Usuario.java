package com.deporte.plantilla.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="usuario")
@Data
public class Usuario {
	
	@Id
	private String usuario;
	private String nombre;
	private String apellido;
	
	@ManyToOne
	@JoinColumn(name="coddocumento", insertable = false, updatable = false)
	private Documento doc;
	private int coddocumento;
	
	private String documento;
	private String correo;
	private String clave;
	
	@ManyToOne
	@JoinColumn(name="codsexo", insertable = false, updatable = false)
	private Sexo sexo;
	private int codsexo;
	
	private String telefono;
	private String direccion;
	private String fecnac;
	
	@ManyToOne
	@JoinColumn(name="codrol", insertable = false, updatable = false)
	private Rol rol;	
	private int codrol;
	
	@ManyToOne
	@JoinColumn(name = "codnivel", insertable = false, updatable = false)
	private Nivel nivel;
	private int codnivel;
	
	@ManyToOne
	@JoinColumn(name = "codestado", insertable = false, updatable = false)
	private Estado estado;
	private int codestado;
	
	private String fecreg;

}
