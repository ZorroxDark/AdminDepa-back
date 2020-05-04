package com.admindepa.web.config;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
//import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
//import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.admindepa.servicios.security.UserDetailsServiceImpl;









@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   @Value("${cors.allowed.origin}")
   private String corsAllowedOrigin;
   // para validar el token
   @Autowired
   private UserDetailsServiceImpl jwtUserDetailsProvider;
   // para autentificar
   @Autowired
   private AuthenticationProvider customAuthenticationProvider;
   // para crear el token si la autentificacion es exitosa
   @Autowired
   private JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHander;
   // para mandar un 401 si la autentificacion es fallida
   @Autowired
   private JwtAuthenticationFailureHandler jwtAuthenticationFailureHander;
   // para manejar cualquier excepcion mandar un 403
   @Autowired
   private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
   /*@Autowired
   private CaptchaFilter captchaFilter;*/

   @Override
   protected void configure(final HttpSecurity http) throws Exception {
		
		  http.cors().and() .csrf().disable() .authorizeRequests()
		  .antMatchers("/login/**").anonymous()
		  .antMatchers("/notificacion/**").permitAll()
		  .antMatchers("/solicitud/**").hasAnyRole("CIUDADANO","DICTAMINADOR","JEFEDEPTO","SUBDIRECTOR","DIRECTOR")
		  .antMatchers(HttpMethod.GET,"/catalogo/**").permitAll()
		  .antMatchers("/documentosAnexos/**").hasAnyRole("CIUDADANO","DICTAMINADOR","JEFEDEPTO","SUBDIRECTOR","DIRECTOR")
		  .antMatchers("/transverales/**").hasAnyRole("CIUDADANO","DICTAMINADOR","JEFEDEPTO","SUBDIRECTOR","DIRECTOR")
		  .antMatchers("/personaFisica/**").hasAnyRole("CIUDADANO","DICTAMINADOR","JEFEDEPTO","SUBDIRECTOR","DIRECTOR")
		  .antMatchers("/asignacion/**").hasAnyRole("DICTAMINADOR","JEFEDEPTO","SUBDIRECTOR","DIRECTOR")
		  .antMatchers("/dictamen/**").hasAnyRole("DICTAMINADOR","JEFEDEPTO","SUBDIRECTOR","DIRECTOR")
		  .antMatchers("/domicilio/**").hasAnyRole("CIUDADANO","DICTAMINADOR","JEFEDEPTO","SUBDIRECTOR","DIRECTOR")
		  .antMatchers("/personaMoral/**").hasAnyRole("CIUDADANO","DICTAMINADOR","JEFEDEPTO","SUBDIRECTOR","DIRECTOR")
		  .antMatchers("/actividadEconomica/**").hasAnyRole("CIUDADANO","DICTAMINADOR","JEFEDEPTO","SUBDIRECTOR","DIRECTOR")
		  .antMatchers("/personaFisicaBDI/**").hasAnyRole("CIUDADANO","DICTAMINADOR","JEFEDEPTO","SUBDIRECTOR","DIRECTOR")
		  .anyRequest().authenticated() .and() .addFilter(new
		  JWTAuthenticationFilter(authenticationManager())) .addFilter(new
		  JWTAuthorizationFilter(authenticationManager(),jwtUserDetailsProvider))
		  .sessionManagement() .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		   
   }

   @Bean
   CorsConfigurationSource corsConfigurationSource() {

      final CorsConfiguration cors = new CorsConfiguration();
      cors.addAllowedOrigin(corsAllowedOrigin);
      cors.setAllowCredentials(true);
      cors.addAllowedHeader("*");
      cors.addAllowedMethod("*");
      cors.addExposedHeader("Authorization");
      cors.addExposedHeader("Content-Disposition");
      cors.addExposedHeader("x-auth-token");

      final UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
      configurationSource.registerCorsConfiguration("/**", cors);

      return configurationSource;
   }

   /*@Bean
   PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   HttpSessionIdResolver httpSessionIdResolver() {
      return HeaderHttpSessionIdResolver.xAuthToken();
   }*/
}
