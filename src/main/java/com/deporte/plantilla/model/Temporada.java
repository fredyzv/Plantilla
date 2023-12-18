package com.deporte.plantilla.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="temporada")
@Data
public class Temporada {
	
	@Id
	private int codtemporada;
	private String nomtemporada;
	@ManyToOne
	@JoinColumn(name = "codestado", insertable = false, updatable = false)
	private Estado estado;
	private int codestado;

}
