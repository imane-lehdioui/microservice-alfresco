package com.pj.alfresco.Models;

public class ObjectBytes {
	long id;
	String name;
	String idAlfresco;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getIdAlfresco() {
		return idAlfresco;
	}
	public void setIdAlfresco(String idAlfresco) {
		this.idAlfresco = idAlfresco;
	}
	public ObjectBytes(long id ,String name, String idAlfresco) {
		super();
		this.id=id;
		this.name = name;
		this.idAlfresco = idAlfresco;
	}
	public ObjectBytes() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}

