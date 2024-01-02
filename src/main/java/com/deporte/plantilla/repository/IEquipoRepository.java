package com.deporte.plantilla.repository;

import com.deporte.plantilla.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEquipoRepository extends JpaRepository<Equipo, Integer>{

	Equipo findByUsuarioAndCodestado(String usuario,int codestado);

	List<Equipo> findByCodliga(int codliga);

	List<Equipo> findByCodestado(int codestado);

}
