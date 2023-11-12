package com.deporte.plantilla.repository;

import com.deporte.plantilla.model.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPosicionRepository extends JpaRepository<Posicion, Integer>{

}
