//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pj.alfresco.Models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(
        name = "PjIMMo"
)
public class ImmPJ {
    @Id
    @GeneratedValue
    @Column(
            name = "Id",
            nullable = false
    )
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
        return this.idImm;
    }

    public void setIdImm(long idImm) {
        this.idImm = idImm;
    }

    public PjImm getType() {
        return this.type;
    }

    public void setType(PjImm type) {
        this.type = type;
    }

    public long getfSize() {
        return this.fSize;
    }

    public void setfSize(long fSize) {
        this.fSize = fSize;
    }

    public Date getDateFile() {
        return this.dateFile;
    }

    public void setDateFile(Date dateFile) {
        this.dateFile = dateFile;
    }

    public ImmPJ() {
    }

    public String getSousModule() {
        return this.sousModule;
    }

    public void setSousModule(String sousModule) {
        this.sousModule = sousModule;
    }

    public ImmPJ(long id, String idAlfresco, String name, long idAo, long fSize, Date dateFile, PjImm type, String sModule) {
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
