package com.deporte.plantilla.repository;

import com.deporte.plantilla.model.Sexo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISexoRepository extends JpaRepository<Sexo, Integer>{

}
