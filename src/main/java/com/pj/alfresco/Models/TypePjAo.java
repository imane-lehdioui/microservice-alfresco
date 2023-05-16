package com.pj.alfresco.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * Added on 212.09.2020 by RS
 *
 */
@Entity
@Table(name = "PjTypePjAo")
public class TypePjAo {
	@Id
	@GeneratedValue
	@Column(name = "Id", nullable = false)
	private long id;

	private String libelle;

	public TypePjAo() {
	}

	public TypePjAo(long id, String libelle) {
		this.id = id;
		this.libelle = libelle;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}
