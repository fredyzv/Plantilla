package com.deporte.plantilla.repository;

import com.deporte.plantilla.model.Datos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDatosRepository extends JpaRepository<Datos, Integer> {

    List<Datos> findByCodpartido(int codpartido);

    List<Datos> findByCodjugador(int codjugador);


}
