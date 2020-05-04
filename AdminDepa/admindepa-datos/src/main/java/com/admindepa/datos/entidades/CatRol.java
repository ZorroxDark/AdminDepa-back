package com.admindepa.datos.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cat_rol database table.
 * 
 */
@Entity
@Table(name="cat_rol")
@NamedQuery(name="CatRol.findAll", query="SELECT c FROM CatRol c")
public class CatRol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_cat_rol")
	private int idCatRol;

	@Column(name="clave_rol")
	private String claveRol;

	private String desc;

	private String nombre;

	private int status;

	//bi-directional many-to-one association to UsuarioRole
	@OneToMany(mappedBy="catRol")
	private List<UsuarioRole> usuarioRoles;

	public CatRol() {
	}

	public int getIdCatRol() {
		return this.idCatRol;
	}

	public void setIdCatRol(int idCatRol) {
		this.idCatRol = idCatRol;
	}

	public String getClaveRol() {
		return this.claveRol;
	}

	public void setClaveRol(String claveRol) {
		this.claveRol = claveRol;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<UsuarioRole> getUsuarioRoles() {
		return this.usuarioRoles;
	}

	public void setUsuarioRoles(List<UsuarioRole> usuarioRoles) {
		this.usuarioRoles = usuarioRoles;
	}

	public UsuarioRole addUsuarioRole(UsuarioRole usuarioRole) {
		getUsuarioRoles().add(usuarioRole);
		usuarioRole.setCatRol(this);

		return usuarioRole;
	}

	public UsuarioRole removeUsuarioRole(UsuarioRole usuarioRole) {
		getUsuarioRoles().remove(usuarioRole);
		usuarioRole.setCatRol(null);

		return usuarioRole;
	}

}