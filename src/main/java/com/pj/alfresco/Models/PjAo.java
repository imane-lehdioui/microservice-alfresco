package com.pj.alfresco.Models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

/**
 * 
 * Added on 212.09.2020 by RS
 *
 */
@Entity
@Table(name = "PjAo")
public class PjAo {
	@Id
	@GeneratedValue
	@Column(name = "Id", nullable = false)
	private long id;
	private String idAlfresco;
	private String name;
	private long idAo;
	private long fSize;
	private String sousModule;
	@CreatedDate
	private Date dateFile;

	@ManyToOne
	private TypePjAo type;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIdAlfresco() {
		return this.idAlfresco;
	}

	public void setIdAlfresco(String idAlfresco) {
		this.idAlfresco = idAlfresco;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getIdAo() {
		return this.idAo;
	}

	public void setIdAo(long idAo) {
		this.idAo = idAo;
	}

	public TypePjAo getType() {
		return this.type;
	}

	public void setType(TypePjAo type) {
		this.type = type;
	}

	public long getfSize() {
		return fSize;
	}

	public void setfSize(long fSize) {
		this.fSize = fSize;
	}

	public Date getDateFile() {
		return dateFile;
	}

	public void setDateFile(Date dateFile) {
		this.dateFile = dateFile;
	}

	public PjAo() {
	}

	public String getSousModule() {
		return sousModule;
	}

	public void setSousModule(String sousModule) {
		this.sousModule = sousModule;
	}

	public PjAo(long id, String idAlfresco, String name, long idAo, long fSize, Date dateFile, TypePjAo type, String sModule) {
		super();
		this.id = id;
		this.idAlfresco = idAlfresco;
		this.name = name;
		this.idAo = idAo;
		this.fSize = fSize;
		this.dateFile = dateFile;
		this.type = type;
		this.sousModule = sModule;
	}

}
