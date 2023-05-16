//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pj.alfresco.Models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(
        name = "PJCourrie"
)
public class PJCourrie {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;
    private String idAlfresco;
    private String name;
    private long idCourrierEntrant;
    private String userName;
    private long fSize;
    @CreatedDate
    private Date dateFile;
    private String motif;

    public PJCourrie() {
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

    public String getMotif() {
        return this.motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getIdCourrierEntrant() {
        return this.idCourrierEntrant;
    }

    public void setIdCourrierEntrant(long idCourrierEntrant) {
        this.idCourrierEntrant = idCourrierEntrant;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
