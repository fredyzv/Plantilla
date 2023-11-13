package com.deporte.plantilla.service;

import java.util.List;

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
	public List<String> traeProvincias(String dpto) {
		return repoUbi.traeProvincias(dpto);
	}


}
