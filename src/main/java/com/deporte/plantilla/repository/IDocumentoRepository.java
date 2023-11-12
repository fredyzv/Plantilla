package com.deporte.plantilla.repository;

import com.deporte.plantilla.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDocumentoRepository extends JpaRepository<Documento, Integer>{

}
