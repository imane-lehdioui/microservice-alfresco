package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pj.alfresco.Models.PjProjetUrbanisme;

/**
 * @author R.SABRI Date de creation:02/04/20
 * 
 */
@Repository
public interface PjProjetUrbanismeRepo extends JpaRepository<PjProjetUrbanisme, Long> {

	List<PjProjetUrbanisme> findByIdProjetUrbanismeOrderByIdDesc(long idProjetUrbanisme);

	public PjProjetUrbanisme findByIdAlfresco(String idAlfresco);

}
