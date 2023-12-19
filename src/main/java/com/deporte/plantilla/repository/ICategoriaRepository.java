package com.deporte.plantilla.repository;

import com.deporte.plantilla.model.Agentes;
import com.deporte.plantilla.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Integer>{

    List<Categoria> findByCodestado(int codestado);

}
