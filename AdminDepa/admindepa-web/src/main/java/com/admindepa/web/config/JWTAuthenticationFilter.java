package com.admindepa.web.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.admindepa.comun.dto.UsuarioDTO;
import com.admindepa.servicios.security.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;




@Slf4j
public class JWTAuthenticationFilter  extends UsernamePasswordAuthenticationFilter {

	
    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("Parametros recibos - username: " + username + " password: " + password);
        
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) throws IOException, ServletException{
   
    	
        UsuarioDTO usuarioDTO = (UsuarioDTO) authentication.getPrincipal();
        log.debug("Datos del usuario: " + usuarioDTO);
        Claims claims = Jwts.claims().setSubject(usuarioDTO.getEmail());
        claims.put("id", usuarioDTO.getIdUsuario());
        claims.put("user", usuarioDTO.getEmail());
        //claims.put("profile", usuarioDTO.getPersona());
        
        String token = Jwts.builder().setClaims(claims)
           .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRES_IN))
           .setHeaderParam("typ", SecurityConstants.JWT_TYPE)
           .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET.getBytes()).compact();
        
        log.debug("Token generado: " + token);
        
        response.addHeader(SecurityConstants.JWT_HEADER_AUTH, SecurityConstants.TOKEN_PREFIX + token);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.print("{\"token\":\""+token+"\"}");
        out.flush();

    }
}


