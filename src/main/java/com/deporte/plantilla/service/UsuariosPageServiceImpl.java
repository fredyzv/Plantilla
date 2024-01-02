package com.deporte.plantilla.service;

import com.deporte.plantilla.model.Usuario;
import com.deporte.plantilla.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsuariosPageServiceImpl implements UsuariosPageService{

    @Autowired
    private IUsuarioRepository repoU;

    @Override
    public Page<Usuario> getAll(Pageable pageable) {
        return repoU.findAll(pageable);
    }
}
