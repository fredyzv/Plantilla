package com.deporte.plantilla.repository;

import com.deporte.plantilla.model.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INivelRepository extends JpaRepository<Nivel, Integer>{

}
