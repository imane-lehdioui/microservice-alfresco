package com.pj.alfresco.Models;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author R.SABRI Date de creation:10/02/20
 * 
 */
@Entity
@Table(name = "PjPc")
public class PjPc {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String idAlfresco;
	private String name;
	private long idPc;
	private long fSize;
	private String sousModule;
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

	public PjPc(long id, String idAlfresco, String name, long idPc) {
		super();
		this.id = id;
		this.idAlfresco = idAlfresco;
		this.name = name;
		this.idPc = idPc;
	}

	public String getSousModule() {
		return sousModule;
	}

	public void setSousModule(String sousModule) {
		this.sousModule = sousModule;
	}

	public long getIdPc() {
		return idPc;
	}

	public void setIdPc(long idPc) {
		this.idPc = idPc;
	}

	public PjPc() {
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

}
