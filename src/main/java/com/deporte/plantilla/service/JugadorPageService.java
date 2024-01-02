package com.deporte.plantilla.service;

import com.deporte.plantilla.model.Jugador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JugadorPageService {

    Page<Jugador> getAll(Pageable pageable);
}
