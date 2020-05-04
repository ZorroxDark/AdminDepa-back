package com.admindepa.servicios.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JwtUserDetails implements UserDetails {

   /**
    * 
    */
   private static final long serialVersionUID = 5074302141928276184L;

   private Long id;
   private String username;
   private String nombreCompleto;
   private boolean habilitado;
   private Collection<? extends GrantedAuthority> authorities;

   public JwtUserDetails(Long id, String username, String nombreCompleto, boolean habilitado,
      Collection<? extends GrantedAuthority> authorities) {
      this.id = id;
      this.username = username;
      this.nombreCompleto = nombreCompleto;
      this.habilitado = habilitado;
      this.authorities = authorities;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return this.authorities;
   }

   @Override
   public String getUsername() {
      return this.username;
   }

   @JsonIgnore
   @Override
   public boolean isAccountNonExpired() {
      // TODO Auto-generated method stub
      return false;
   }

   @JsonIgnore
   @Override
   public boolean isAccountNonLocked() {
      // TODO Auto-generated method stub
      return false;
   }

   @JsonIgnore
   @Override
   public boolean isCredentialsNonExpired() {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean isEnabled() {
      return this.habilitado;
   }

   @JsonIgnore
   public Long getId() {
      return id;
   }

   public String getNombreCompleto() {
      return nombreCompleto;
   }

   @Override
   public String getPassword() {

      return null;
   }

}