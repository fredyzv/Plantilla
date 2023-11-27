package com.deporte.plantilla.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="partido")
@Data
public class Partido {
	
	@Id
	private int codpartido;
	
	@ManyToOne
	@JoinColumn(name="codequipo", insertable = false, updatable = false)
	private Equipo equipo;
	private int codequipo;
	
	private int codtemporada;
	
	private String equiporival;
	private String fecpartido;
	private int scoreequipo;
	private int scorerival;

	
	private int pcequipo;
	private int scequipo;
	private int tcequipo;
	private int ccequipo;
	private int psequipo;
	private int ssequipo;
	private int tsequipo;
	
	private int pcrival;	
	private int scrival;	
	private int tcrival;	
	private int ccrival;	
	private int psrival;	
	private int ssrival;
	private int tsrival;
}
