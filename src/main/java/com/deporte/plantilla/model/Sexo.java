package com.deporte.plantilla.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="sexo")
@Data
public class Sexo {
	
	@Id
	private int codsexo;
	private String descripcion;

}
