package com.admindepa.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/resgistro")
@Slf4j
public class RegistroUsuarioController {
	@GetMapping("/consultaSolicitud/{idSolicitud}")
	public ResponseEntity<String> consultarSolicitud(@PathVariable("idSolicitud") Long idSolicitud) {
		log.debug("id de la solicitud recibida en el servicio: " + idSolicitud);
		//SolicitudDTO solicitudDTO = this.solicitudServe.consultaSolicitud(idSolicitud);
		return new ResponseEntity<String>("Hola", HttpStatus.OK);
	}

}
