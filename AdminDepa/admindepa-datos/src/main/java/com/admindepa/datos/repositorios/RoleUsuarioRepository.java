package com.admindepa.datos.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.admindepa.datos.entidades.UsuarioRole;

public interface RoleUsuarioRepository  extends CrudRepository<UsuarioRole, Long> {

	@Query(	value= "select * from usuario_roles where id_user = :idUser ", nativeQuery = true)
	List<UsuarioRole> findRolesUsuario(@Param("idUser") long idUser);

}
