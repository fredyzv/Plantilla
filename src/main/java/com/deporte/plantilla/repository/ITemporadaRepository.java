package com.deporte.plantilla.repository;

import com.deporte.plantilla.model.Categoria;
import com.deporte.plantilla.model.Temporada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITemporadaRepository extends JpaRepository<Temporada, Integer> {
    List<Temporada> findByCodestado(int codestado);
}
