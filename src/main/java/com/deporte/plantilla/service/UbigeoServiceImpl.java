package com.deporte.plantilla.service;

import java.util.List;

import com.deporte.plantilla.model.Ubigeo;
import com.deporte.plantilla.repository.IUbigeoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UbigeoServiceImpl implements UbigeoService{
	
	@Autowired
	private IUbigeoRepository repoUbi;

	@Override
	public List<String> traeDepartamentos() {
		return repoUbi.traeDepartamentos();
	}

	@Override
	public List<String> traeProvincias(Ubigeo ubigeo) {
		return repoUbi.traeProvincias(ubigeo);
	}

	@Override
	public List<String> traeDistritos(Ubigeo ubigeo) {
		return repoUbi.traeDistritos(ubigeo);
	}


}
