package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pj.alfresco.Models.PjLmarcheDecision;




public interface LmarcheDecisionRepo extends JpaRepository<PjLmarcheDecision, Long> {

	public List<PjLmarcheDecision> findByIdmarche(long id);

	// @Query(value ="select c.NAME from  PJAttestations c where c.ID_ALFRESCO=?1" , nativeQuery=true )
	public PjLmarcheDecision findByIdAlfresco (String idAlfresco);
}
