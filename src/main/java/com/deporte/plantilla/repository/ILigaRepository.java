package com.deporte.plantilla.repository;

import com.deporte.plantilla.model.Liga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILigaRepository extends JpaRepository<Liga, Integer>{

}
