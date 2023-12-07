package com.deporte.plantilla.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name="categoria")
@Data
public class Categoria {
	
	@Id
	private int codcategoria;
	private String categoria;

	@ManyToOne
	@JoinColumn(name = "codestado", insertable = false, updatable = false)
	private Estado estado;
	private int codestado;
}
