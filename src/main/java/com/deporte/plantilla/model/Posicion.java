package com.deporte.plantilla.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="posicion")
@Data
public class Posicion {
	
	@Id
	private int codposicion;
	private String posicion;

}
