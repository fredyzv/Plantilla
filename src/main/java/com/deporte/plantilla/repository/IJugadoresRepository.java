package com.deporte.plantilla.repository;

import com.deporte.plantilla.model.Jugadores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJugadoresRepository extends JpaRepository<Jugadores, String>{
	
	Jugadores findByDocumentoAndClave(String documento, String clave);
	
	Jugadores findByDocumento(String documento);


}
