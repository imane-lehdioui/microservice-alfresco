package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pj.alfresco.Models.PjImmobilisation;

/**
 * @author R.SABRI Date de creation:10/02/20
 * 
 */
@Repository
public interface PjImmobilisationRepo extends JpaRepository<PjImmobilisation, Long> {

	List<PjImmobilisation> findByIdImmobilisationOrderByIdDesc(long idImmobilisation);

	public PjImmobilisation findByIdAlfresco(String idAlfresco);

}
