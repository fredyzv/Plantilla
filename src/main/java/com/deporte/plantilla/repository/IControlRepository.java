package com.deporte.plantilla.repository;

import com.deporte.plantilla.model.Control;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IControlRepository extends JpaRepository<Control, Integer> {

    Control findByUsuario(String usuario);

}
