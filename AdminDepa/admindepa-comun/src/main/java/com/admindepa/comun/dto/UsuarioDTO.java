package com.admindepa.comun.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UsuarioDTO  implements java.io.Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1505527231209476977L;
	
	private long idUsuario;
	private String nombre;
	private String apellido;
	private String email;
	private String pwd;
	private int status;
	private Date fechaAlta;
	private Date fechaMod;
	private List<String> roles = new ArrayList<String>(); 
	
	

}
