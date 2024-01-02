package com.deporte.plantilla.service;

import com.deporte.plantilla.model.Equipo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EquipoPageService {

    Page<Equipo> getAll(Pageable pageable);
}
