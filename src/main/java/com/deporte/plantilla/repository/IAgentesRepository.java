package com.deporte.plantilla.repository;

import com.deporte.plantilla.model.Agentes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAgentesRepository extends JpaRepository < Agentes, Integer> {

    List<Agentes> findByCodjugadorAndCodestado(int codjugador, int codestado);

    Agentes findByCodjugadorAndCodequipo(int codjugador, int codequipo);

}
