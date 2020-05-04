package com.admindepa.datos.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the usuario_roles database table.
 * 
 */
@Entity
@Table(name="usuario_roles")
@NamedQuery(name="UsuarioRole.findAll", query="SELECT u FROM UsuarioRole u")
public class UsuarioRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_rol_usuario")
	private int idRolUsuario;

	//bi-directional many-to-one association to CatRol
	@ManyToOne
	@JoinColumn(name="id_cat_rol")
	private CatRol catRol;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_user")
	private Usuario usuario;

	public UsuarioRole() {
	}

	public int getIdRolUsuario() {
		return this.idRolUsuario;
	}

	public void setIdRolUsuario(int idRolUsuario) {
		this.idRolUsuario = idRolUsuario;
	}

	public CatRol getCatRol() {
		return this.catRol;
	}

	public void setCatRol(CatRol catRol) {
		this.catRol = catRol;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}