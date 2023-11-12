package com.deporte.plantilla.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="liga")
@Data
public class Liga {
	
	@Id
	private int codliga;
	private String nombre;
	
	@ManyToOne
	@JoinColumn(name="ubigeo1", insertable = false, updatable = false)
	private Ubigeo ubi;
	private int ubigeo1;

}
