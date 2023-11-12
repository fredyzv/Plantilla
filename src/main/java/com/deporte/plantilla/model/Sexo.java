package com.deporte.plantilla.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="sexo")
@Data
public class Sexo {
	
	@Id
	private int codsexo;
	private String descripcion;

}
