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
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(
        name = "ConvnetionMarche"
)
public class ConvnetionMarche {
    @Id
    @GeneratedValue
    @Column(
            name = "Id",
            nullable = false
    )
    private long id;
    private String idAlfresco;
    private String name;
    private long idConvention;
    private long fSize;
    private String sousModule;
    @CreatedDate
    private Date dateFile;

    public ConvnetionMarche() {
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

    public long getIdConvention() {
        return this.idConvention;
    }

    public void setIdConvention(long idConvention) {
        this.idConvention = idConvention;
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

    public String getSousModule() {
        return this.sousModule;
    }

    public void setSousModule(String sousModule) {
        this.sousModule = sousModule;
    }
}
