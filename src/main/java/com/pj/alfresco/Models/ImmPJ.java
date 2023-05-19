package com.pj.alfresco.Models;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;



@Entity
@Table(name = "PjIMMo")
public class ImmPJ {
    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false)
    private long id;
    private String idAlfresco;
    private String name;
    private long idImm;
    private long fSize;
    private String sousModule;
    @CreatedDate
    private Date dateFile;

    @ManyToOne
    private PjImm type;

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

    public long getIdImm() {
        return idImm;
    }

    public void setIdImm(long idImm) {
        this.idImm = idImm;
    }

    public PjImm getType() {
        return type;
    }

    public void setType(PjImm type) {
        this.type = type;
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

    public ImmPJ() {
    }

    public String getSousModule() {
        return sousModule;
    }

    public void setSousModule(String sousModule) {
        this.sousModule = sousModule;
    }

    public ImmPJ(long id, String idAlfresco, String name, long idAo, long fSize, Date dateFile, PjImm type, String sModule) {
        super();
        this.id = id;
        this.idAlfresco = idAlfresco;
        this.name = name;
        this.idImm = idAo;
        this.fSize = fSize;
        this.dateFile = dateFile;
        this.type = type;
        this.sousModule = sModule;
    }

}
