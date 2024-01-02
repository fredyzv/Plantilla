package com.deporte.plantilla.repository;

import com.deporte.plantilla.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, String>{

	Usuario findByCorreoAndClave(String correo, String clave);

	Usuario findByCorreo(String correo);

	List<Usuario> findByCodestado(int codestado);

	Usuario findByDocumento(String documento);

	Usuario findByTelefono(String telefono);

	List<Usuario> findByCodrolAndCodestado(int codrol, int codestado);

}
