package com.deporte.plantilla.service;

import com.deporte.plantilla.model.Jugador;
import com.deporte.plantilla.repository.IJugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JugadorPageServiceImpl implements JugadorPageService{

    @Autowired
    private IJugadorRepository repoJ;

    @Override
    public Page<Jugador> getAll(Pageable pageable) {
        return repoJ.findAll(pageable);
    }
}
