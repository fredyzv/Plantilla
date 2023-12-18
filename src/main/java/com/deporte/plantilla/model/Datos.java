package com.deporte.plantilla.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="datos")
@Data
public class Datos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int coddatos;

    @ManyToOne
    @JoinColumn(name="codjugador", insertable = false, updatable = false)
    private Jugador jugador;
    private int codjugador;

    @ManyToOne
    @JoinColumn(name="codpartido", insertable = false, updatable = false)
    private Partido partido;
    private int codpartido;

    private int ilibres;
    private int clibres;

    private int idobles;
    private int cdobles;

    private int itriples;
    private int ctriples;

    private int rebdef;
    private int rebofe;

    private int asist;
    private int recup;
    private int perd;

    private int tapfav;
    private int tapcont;

    private int faltdadas;
    private int faltasrec;

    private String tiempo;
}
