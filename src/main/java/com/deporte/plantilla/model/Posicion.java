package com.deporte.plantilla.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="posicion")
@Data
public class Posicion {
	
	@Id
	private int codposicion;
	private String posicion;

}
