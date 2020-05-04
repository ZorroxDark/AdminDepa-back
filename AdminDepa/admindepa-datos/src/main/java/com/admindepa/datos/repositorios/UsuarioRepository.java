package com.admindepa.datos.repositorios;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.admindepa.datos.entidades.Usuario;

public interface UsuarioRepository  extends CrudRepository<Usuario, Long> {
	
	
	@Query(	value= "select count(*) from usuarios where status = 1 and id_usuarios = :idUser ", nativeQuery = true)
	int existeUsuario(@Param("idUser") long idUser); 
	
	@Modifying
	@Query("update Usuario set status = 2 where id_usuarios= :idUser")
	void updateUsuarioConfirmacion(@Param("idUser") long idUser);
	
	Usuario findByEmail(String correo);

}
