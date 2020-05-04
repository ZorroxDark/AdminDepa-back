package com.admindepa.web.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.admindepa.servicios.security.SecurityConstants;
import com.admindepa.servicios.security.UserDetailsServiceImpl;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

   private UserDetailsServiceImpl userDetailsServiceImpl;

   public JWTAuthorizationFilter(AuthenticationManager authManager, UserDetailsServiceImpl jwtUserDetailsProvider) {
      super(authManager);
      this.userDetailsServiceImpl = jwtUserDetailsProvider;

   }

   @Override
   protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws IOException, ServletException {
	   
      String header = req.getHeader(SecurityConstants.JWT_HEADER_AUTH);
      log.debug("Autorizar el header de la peticion: " + header);
      
      if (null == header || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
         chain.doFilter(req, res);
         return;
      }

      UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

      SecurityContextHolder.getContext().setAuthentication(authentication);
      chain.doFilter(req, res);
   }

   private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
      String token = request.getHeader(SecurityConstants.JWT_HEADER_AUTH);
      if (null != token  && StringUtils.isNotBlank(token.replace(SecurityConstants.TOKEN_PREFIX, ""))) {
         // parse the token.
         String user = Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET.getBytes()).parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, "")).getBody()
            .getSubject();

         if (user != null) {
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(user);
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
         }
         return null;
      }
      return null;
   }

}
