package com.deporte.plantilla.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="agentes")
@Data
public class Agentes {

    @Id
    private int codagente;

    @ManyToOne
    @JoinColumn(name="codjugador", insertable = false, updatable = false)
    private Jugador jugador;
    private int codjugador;

    @ManyToOne
    @JoinColumn(name="codequipo", insertable = false, updatable = false)
    private Equipo equipo;
    private int codequipo;

    private String fecreg;

    @ManyToOne
    @JoinColumn(name = "codestado", insertable = false, updatable = false)
    private Estado estado;
    private int codestado;
}
