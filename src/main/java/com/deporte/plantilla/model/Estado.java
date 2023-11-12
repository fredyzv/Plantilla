package com.deporte.plantilla.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="estado")
@Data
public class Estado {
	
	@Id
	private int codestado;
	private String estado;

}
