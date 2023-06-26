package com.pj.alfresco.Models;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "ProgrammeMarche")
public class ProgrammeMarche {
    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false)
    private long id;
    private String idAlfresco;
    private String name;
    private long idProgramme;
    private long fSize;
    private String sousModule;
    @CreatedDate
    private Date dateFile;


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

    public long getIdProgramme() {
        return idProgramme;
    }

    public void setIdProgramme(long idProgramme) {
        this.idProgramme = idProgramme;
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



    public String getSousModule() {
        return sousModule;
    }

    public void setSousModule(String sousModule) {
        this.sousModule = sousModule;
    }



}
