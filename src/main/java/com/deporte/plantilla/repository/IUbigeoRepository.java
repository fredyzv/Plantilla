package com.deporte.plantilla.repository;

import java.util.List;

import com.deporte.plantilla.model.Ubigeo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUbigeoRepository extends JpaRepository<Ubigeo, String>{
	
	@Query("select distinct x.dpto from Ubigeo x")
	public abstract List<String> traeDepartamentos();
	
	/*@Query("select distinct x.prov from ubigeo x where x.dpto")
	public abstract List<String> traeProvincias(Ubigeo obj);*/

}
