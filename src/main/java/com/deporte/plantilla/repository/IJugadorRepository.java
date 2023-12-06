package com.deporte.plantilla.repository;

import com.deporte.plantilla.model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IJugadorRepository extends JpaRepository<Jugador, Integer>{
	
	Jugador findByDocumento(String documento);
	
	List<Jugador> findByCodequipoAndCodestado(int codequipo,int codestado);

	List<Jugador> findByCodestado(int codestado);

	List<Jugador> findByCodcategoriaAndCodequipo(int codcategoria, int codequipo);

}
