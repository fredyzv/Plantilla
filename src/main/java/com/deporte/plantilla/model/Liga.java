package com.deporte.plantilla.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="liga")
@Data
public class Liga {
	
	@Id
	private int codliga;
	private String nomliga;
	private String dep;
	private String pro;
	private String dis;

	@ManyToOne
	@JoinColumn(name="codestado", insertable = false, updatable = false)
	private Estado est;
	private int codestado;

	private int num;

}
