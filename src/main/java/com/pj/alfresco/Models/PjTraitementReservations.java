package com.pj.alfresco.Models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

/**
 * 
 * Added on 22.09.2020 by RS
 *
 */
@Entity
@Table(name = "PjTraitementReservations")
public class PjTraitementReservations {
	@Id
	@GeneratedValue
	@Column(name = "Id", nullable = false)
	private long id;
	private String idAlfresco;
	private String name;
	private long idReservation;
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

	public long getIdReservation() {
		return this.idReservation;
	}

	public void setIdReservation(long idReservation) {
		this.idReservation = idReservation;
	}

	public PjTraitementReservations(long id, String idAlfresco, String name, long idReservation, long fSize,
			Date dateFile) {
		super();
		this.id = id;
		this.idAlfresco = idAlfresco;
		this.name = name;
		this.idReservation = idReservation;
		this.fSize = fSize;
		this.dateFile = dateFile;
	}

	public PjTraitementReservations() {
	}
}
