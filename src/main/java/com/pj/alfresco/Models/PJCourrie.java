package com.pj.alfresco.Models;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "PJCourrie")
public class PJCourrie {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String idAlfresco;
  private String name;
  private long idCourrierEntrant;
  private  String userName;
  private long fSize;
  @CreatedDate
  private Date dateFile;
   private String motif;
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

  public String getMotif() {
    return motif;
  }

  public void setMotif(String motif) {
    this.motif = motif;
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

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
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
