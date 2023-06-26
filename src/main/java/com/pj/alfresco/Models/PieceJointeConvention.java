package com.pj.alfresco.Models;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PieceJointeConvention")
public class PieceJointeConvention {

    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false)
    private long id;
    private String idAlfresco;
    private String name;
    private long idConvention;
    private long fSize;
    private String sousModule;
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

    public long getIdConvention() {
        return idConvention;
    }

    public void setIdConvention(long idConvention) {
        this.idConvention = idConvention;
    }

    public long getfSize() {
        return fSize;
    }

    public void setfSize(long fSize) {
        this.fSize = fSize;
    }

    public String getSousModule() {
        return sousModule;
    }

    public void setSousModule(String sousModule) {
        this.sousModule = sousModule;
    }

    public Date getDateFile() {
        return dateFile;
    }

    public void setDateFile(Date dateFile) {
        this.dateFile = dateFile;
    }
}
