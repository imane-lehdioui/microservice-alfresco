package com.pj.alfresco.Models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "PjUsers")
public class PjUsers {
	@Id
	@GeneratedValue
	@Column(name = "Id", nullable = false)
	private long id;
	private String idAlfresco;
	private String name;
	private long idUser;
	private long fSize;
	@CreatedDate
	private Date dateFile;

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

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
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

	public PjUsers() {
		super();
	}

	public PjUsers(long id, String idAlfresco, String name, long idUser, long fSize, Date dateFile) {
		super();
		this.id = id;
		this.idAlfresco = idAlfresco;
		this.name = name;
		this.idUser = idUser;
		this.fSize = fSize;
		this.dateFile = dateFile;
	}

}
