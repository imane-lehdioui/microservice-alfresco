package com.pj.alfresco.Models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

/**
 * @author R.SABRI Date de creation:10/02/20
 * 
 */
@Entity
@Table(name = "PjAssociation")
public class PjAssociation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String idAlfresco;
	private String name;
	private long idAssociation;
	private long fSize;
	@CreatedDate
	private Date dateFile;
	private String typePJ;

	// getters & setters

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

	public long getIdAssociation() {
		return idAssociation;
	}

	public void setIdAssociation(long idAssociation) {
		this.idAssociation = idAssociation;
	}

	public PjAssociation() {
		super();
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

	public String getTypePJ() {
		return typePJ;
	}

	public void setTypePJ(String typePJ) {
		this.typePJ = typePJ;
	}

}
