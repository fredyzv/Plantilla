package com.deporte.plantilla.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="rol")
@Data
public class Rol {
	
	@Id
	private int codrol;
	private String descripcion;

}
