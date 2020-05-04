package com.admindepa.web.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.admindepa.comun.dto.UsuarioDTO;
import com.admindepa.servicios.security.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
   public JwtAuthenticationSuccessHandler() {
      super();
      setRedirectStrategy(new NoRedirectStrategy());
   }

   @Override
   public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
      super.onAuthenticationSuccess(request, response, authentication);
      clearAuthenticationAttributes(request);
      UsuarioDTO usuarioDTO = (UsuarioDTO) authentication.getPrincipal();
      Claims claims = Jwts.claims().setSubject(usuarioDTO.getEmail());
      claims.put("id", usuarioDTO.getIdUsuario());
      claims.put("user", usuarioDTO.getEmail());
      //claims.put("role", usuarioDTO.getRoles());
      //claims.put("profile", usuarioDTO.getPersona());

      String token = Jwts.builder().setClaims(claims).setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRES_IN))
         .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET.getBytes()).compact();

      response.addHeader(SecurityConstants.JWT_HEADER_AUTH, SecurityConstants.TOKEN_PREFIX + token);
      response.setContentType("application/json");
      response.setCharacterEncoding("utf-8");
      PrintWriter out = response.getWriter();
      out.print("{\"token\":\""+token+"\"}");
      out.flush();

   }
   /**
    * Evitar el redireccionamiento cuando la autenticacion fue exitosa
    *
    */
   protected class NoRedirectStrategy implements RedirectStrategy {

      @Override
      public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
       
      }

   }

}