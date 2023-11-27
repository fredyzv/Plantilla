package com.deporte.plantilla.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="jugadores")
@Data
public class Jugadores {
	
	@Id
	private String documento;
	private String nombre;
	private String apellido;
	private String clave;
	private String fecreg;

}
