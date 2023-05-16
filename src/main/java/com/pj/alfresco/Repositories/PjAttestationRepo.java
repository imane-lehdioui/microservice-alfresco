package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pj.alfresco.Models.PjAttestation;

public interface PjAttestationRepo extends JpaRepository<PjAttestation, Long> {
	
	 public List<PjAttestation> findAllByIdAttestation(long id);
	 
	 PjAttestation findByIdAttestation(long id);
	 
	 @Query(value ="select c.NAME from  PJAttestations c where c.ID_ALFRESCO=?1" , nativeQuery=true )
		String findByIdAlfresco (String idAlfresco);
}


