package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pj.alfresco.Models.PjHebergement;

/**
 * @author R.SABRI Date de creation:02/04/20
 * 
 */
@Repository
public interface PjHebergementRepo extends JpaRepository<PjHebergement, Long> {

	List<PjHebergement> findByIdHebergementOrderByIdDesc(long idHebergement);

	public PjHebergement findByIdAlfresco(String idAlfresco);

}
