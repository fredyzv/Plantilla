package com.deporte.plantilla.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="jugador")
@Data
public class Jugador {
	
	@Id
	private int codjugador;
	private String nomjug;
	private String apejug;

	@ManyToOne
	@JoinColumn(name="coddocumento", insertable = false, updatable = false)
	private Documento doc;
	private int coddocumento;

	private String documento;
	
	@ManyToOne
	@JoinColumn(name="codsexo", insertable = false, updatable = false)
	private Sexo sexo;
	private int codsexo;
	
	@ManyToOne
	@JoinColumn(name="codposicion", insertable = false, updatable = false)
	private Posicion posicion;
	private int codposicion;
	
	@ManyToOne
	@JoinColumn(name="codequipo", insertable = false, updatable = false)
	private Equipo equipo;
	private int codequipo;
	
	@ManyToOne
	@JoinColumn(name="codcategoria", insertable = false, updatable = false)
	private Categoria categoria;
	private int codcategoria;
	
	private String email;
	private String telefono;
	private String direccion;
	private double talla;
	private double peso;
	private int numero;
	private String fecnac;
	private String fecreg;

	@ManyToOne
	@JoinColumn(name = "codestado", insertable = false, updatable = false)
	private Estado estado;
	private int codestado;

}
