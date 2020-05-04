package com.admindepa.servicios.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admindepa.comun.dto.UsuarioDTO;
import com.admindepa.datos.entidades.Usuario;
import com.admindepa.datos.entidades.UsuarioRole;
import com.admindepa.datos.repositorios.RoleUsuarioRepository;
import com.admindepa.datos.repositorios.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

   @Autowired
   private UsuarioRepository usuarioDAO;
   @Autowired
   private RoleUsuarioRepository roleUsuarioRepository;
  
   
 @Override
 @Transactional
 public Authentication authenticate(final Authentication authentication) {
	 
	 final String username = (String) authentication.getPrincipal();
	 
	 //A) Valido que exista en nuestra BD el usuario 
	 Usuario usuario = usuarioDAO.findByEmail(username);
	 
	 if (usuario == null ){
		 final String error = String.format("El usuario %s no se encuentra registrado", username);
	     log.error(error);
	     throw new UsernameNotFoundException(error);
	 }
		 
	 final String credentials = (String) authentication.getCredentials();
	 
	 log.debug("Credentials user: " + credentials); 
	       
	 if (!StringUtils.equals(usuario.getPwd(), credentials)) {
	       final String error = String.format("Imposible autenticar al usuario %s con las credenciales recibidas", username);
	       log.error(error);
	       throw new BadCredentialsException(error);
	 }
	       
	 final ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	 
	 //final ArrayList<String> roles = new ArrayList<String>();
	 List<UsuarioRole> listaRoles = new ArrayList<UsuarioRole>();
	 listaRoles.addAll(roleUsuarioRepository.findRolesUsuario(usuario.getIdUsuario()));
	 
	 for(UsuarioRole rolUser:listaRoles) {
		 
		 authorities.add(new SimpleGrantedAuthority(new StringBuilder(rolUser.getCatRol().getClaveRol()).toString()));
	 }
	 
	
	 //roles.add("ROLE_"+"ADMIN");
	 	   
	 final UsuarioDTO principal = new UsuarioDTO();
	 
	 
	 principal.setApellido(usuario.getApellido());
	 principal.setEmail(usuario.getEmail());
	 principal.setFechaAlta(usuario.getFechaAlta());
	 principal.setFechaMod(usuario.getFechaMod());
	 principal.setIdUsuario(usuario.getIdUsuario());
	 principal.setNombre(usuario.getNombre());
	 principal.setStatus(1);
	     

	 
	 
	 return new UsernamePasswordAuthenticationToken(principal, credentials, authorities);
 }
 
 	
   


 @Override
   public boolean supports(final Class<?> authentication) {
      return UsernamePasswordAuthenticationToken.class.equals(authentication);
   }

   private String concatenaRoles(Collection<GrantedAuthority> authorities) {
      if (authorities.isEmpty()) {
         return "";
      }
      StringBuilder sb = new StringBuilder();
      for (final GrantedAuthority authority : authorities) {
         sb.append(authority.toString()).append(",");

      }
      return sb.substring(0, sb.length() - 1);
   }
}
