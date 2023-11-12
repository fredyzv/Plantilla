package com.deporte.plantilla.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="nivel")
@Data
public class Nivel {
	
	@Id
	private int codnivel;
	private String nivel;

}
