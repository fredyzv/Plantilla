package com.deporte.plantilla.repository;

import com.deporte.plantilla.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPartidoRepository extends JpaRepository<Partido, Integer>{
	
	List<Partido> findByCodequipo(int codequipo);


}
