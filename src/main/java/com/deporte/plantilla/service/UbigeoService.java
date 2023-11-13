package com.deporte.plantilla.service;

import java.util.List;

public interface UbigeoService {
	
	public abstract List<String> traeDepartamentos();

	public abstract List<String> traeProvincias(String dpto);

}
