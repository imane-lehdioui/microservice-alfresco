package com.pj.alfresco.Models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;

@Entity
public class PjLigneLoyerToilette {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String idAlfresco;
	private String name;
	private long idLoyer;
	private String type;
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

	public PjLigneLoyerToilette() {
		super();

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

	public long getIdLoyer() {
		return idLoyer;
	}

	public void setIdLoyer(long idLoyer) {
		this.idLoyer = idLoyer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
