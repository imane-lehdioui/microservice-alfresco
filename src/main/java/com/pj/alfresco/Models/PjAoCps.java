package com.pj.alfresco.Models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "PjAoCps")
public class PjAoCps {
	@Id
	@GeneratedValue
	@Column(name = "Id", nullable = false)
	private long id;
	private String idAlfresco;
	private String name;
	private long idAo;
	private long fSize;
	@CreatedDate
	private Date dateFile;

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIdAlfresco() {
		return idAlfresco;
	}

	public void setIdAlfresco(String idAlfresco) {
		this.idAlfresco = idAlfresco;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getIdAo() {
		return idAo;
	}

	public void setIdAo(long idAo) {
		this.idAo = idAo;
	}

	public PjAoCps(long id, String idAlfresco, String name, long idAo, long fSize, Date dateFile) {
		super();
		this.id = id;
		this.idAlfresco = idAlfresco;
		this.name = name;
		this.idAo = idAo;
		this.fSize = fSize;
		this.dateFile = dateFile;
	}

	public PjAoCps() {
		super();
	}

}
