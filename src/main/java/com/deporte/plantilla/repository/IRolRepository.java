package com.deporte.plantilla.repository;

import com.deporte.plantilla.model.Estado;
import com.deporte.plantilla.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolRepository extends JpaRepository<Rol, Integer> {
}
