package com.deporte.plantilla.repository;

import com.deporte.plantilla.model.Ubigeo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IUbigeoRepository extends JpaRepository<Ubigeo, String>{
	
	@Query("select distinct x.dpto from Ubigeo x")
	public abstract List<String> traeDepartamentos();

	@Query("select distinct x.prov from Ubigeo x where x.dpto = :#{#obj.dpto}")
	public abstract List<String> traeProvincias(Ubigeo obj);

	@Query("select distinct x.distrito from Ubigeo x where x.prov = :#{#obj.prov}")
	public abstract List<String> traeDistritos(Ubigeo obj);

	Ubigeo findByProvAndDistrito(String prov, String distrito);

}
