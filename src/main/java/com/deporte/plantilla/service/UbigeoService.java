package com.deporte.plantilla.service;

import com.deporte.plantilla.model.Ubigeo;

import java.util.List;

public interface UbigeoService {
	
	public abstract List<String> traeDepartamentos();

	public abstract List<String> traeProvincias(Ubigeo ubigeo);

}
