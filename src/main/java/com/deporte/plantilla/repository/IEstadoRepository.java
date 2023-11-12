package com.deporte.plantilla.repository;

import com.deporte.plantilla.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface IEstadoRepository extends JpaRepository<Estado, Integer> {

}
