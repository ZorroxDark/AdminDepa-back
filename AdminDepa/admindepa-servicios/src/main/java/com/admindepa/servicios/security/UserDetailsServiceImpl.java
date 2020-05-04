package com.admindepa.servicios.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admindepa.datos.entidades.Usuario;
import com.admindepa.datos.entidades.UsuarioRole;
import com.admindepa.datos.repositorios.RoleUsuarioRepository;
import com.admindepa.datos.repositorios.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

   @Autowired
   private  UsuarioRepository usuarioDAO;
   @Autowired
   private RoleUsuarioRepository roleUsuarioRepository;
  
   @Override
   public UserDetails loadUserByUsername(String username) {
      
	  log.debug("Se obtienen los datos del usuario: " + username);
	  
	 //A) Valido que exista en nuestra BD el usuario 
	 Usuario usuario = usuarioDAO.findByEmail(username);
	 	 
	 if (usuario == null ){
	 
	 	throw new UsernameNotFoundException("El usuario no fue localizado con el username " + username);
	 }
	  
	 final ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
	 	 
		 
	 for(UsuarioRole rolUser:roleUsuarioRepository.findRolesUsuario(usuario.getIdUsuario())) {
	 		 
		 grantedAuthorities.add(new SimpleGrantedAuthority(new StringBuilder(rolUser.getCatRol().getClaveRol()).toString()));
	 }
    


	
	 
     return new JwtUserDetails(usuario.getIdUsuario()
    		 , usuario.getEmail(),
         new StringBuilder(
        		 usuario.getNombre()).append(" ").append(usuario.getApellido()).toString(),
         true, grantedAuthorities);
      
      
      
 	     

   }

}