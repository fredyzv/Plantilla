package com.deporte.plantilla.service;

import com.deporte.plantilla.model.Equipo;
import com.deporte.plantilla.repository.IEquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EquipoPageServiceImpl implements EquipoPageService{

    @Autowired
    private IEquipoRepository repoE;

    @Override
    public Page<Equipo> getAll(Pageable pageable) {
        return repoE.findAll(pageable);
    }
}
