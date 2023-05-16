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
@Table(name = "PjCourriersEntrants")
public class PjCourriersEntrants {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String idAlfresco;
	private String name;
	private long idCourrierEntrant;
	private long fSize;
	// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@CreatedDate
	private Date dateFile;

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

	public long getIdCourrierEntrant() {
		return idCourrierEntrant;
	}

	public void setIdCourrierEntrant(long idCourrierEntrant) {
		this.idCourrierEntrant = idCourrierEntrant;
	}

	public PjCourriersEntrants() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Date getDateFile() {
		return dateFile;
	}

	public void setDateFile(Date dateFile) {
		this.dateFile = dateFile;
	}

	public long getfSize() {
		return fSize;
	}

	public void setfSize(long fSize) {
		this.fSize = fSize;
	}

}
