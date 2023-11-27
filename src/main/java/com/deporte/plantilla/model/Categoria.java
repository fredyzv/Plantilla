package com.deporte.plantilla.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name="categoria")
@Data
public class Categoria {
	
	@Id
	private int codcategoria;
	private String categoria;

}
