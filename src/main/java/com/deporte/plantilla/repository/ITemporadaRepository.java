package com.deporte.plantilla.repository;

import com.deporte.plantilla.model.Temporada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITemporadaRepository extends JpaRepository<Temporada, Integer> {
}
