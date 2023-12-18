package com.deporte.plantilla.service;

import com.deporte.plantilla.model.Datos;
import com.deporte.plantilla.repository.IDatosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatosServiceImpl implements DatosService{

    @Autowired
    private IDatosRepository repoD;

    @Override
    public void insertaRegistros(List<Datos> list) {
        repoD.saveAll(list);
    }
}
