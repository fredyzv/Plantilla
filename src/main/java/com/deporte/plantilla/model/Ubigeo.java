package com.deporte.plantilla.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="ubigeo")
@Data
public class Ubigeo {
	
	@Id
	private String ubigeo1;
	private String dpto;
	private String prov;
	private String distrito;

}
