package com.deporte.plantilla.model;

import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="documento")
@Data
public class Documento {
	
	@Id
	private int coddocumento;
	private String documento;

}
