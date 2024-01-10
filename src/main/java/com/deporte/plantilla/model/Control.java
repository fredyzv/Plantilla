package com.deporte.plantilla.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="control")
@Data
public class Control {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codcontrol;

    @ManyToOne
    @JoinColumn(name="usuario", insertable = false, updatable = false)
    private Usuario usucontrol;
    private String usuario;

    private String fecini;
    private String fecact;
    private boolean situacion;
    private int meses;

}
