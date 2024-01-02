package com.deporte.plantilla.service;

import com.deporte.plantilla.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuariosPageService {

    Page<Usuario> getAll(Pageable pageable);

}
