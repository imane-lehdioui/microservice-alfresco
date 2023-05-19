package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pj.alfresco.Models.PjPhaseProjet;


public interface PhaseRepo extends JpaRepository<PjPhaseProjet, Long> {
	public List<PjPhaseProjet> findByIdPhase(long id);
	public List<PjPhaseProjet> findByIdPhaseAndType(long id,String type);
	public PjPhaseProjet findOneByIdPhaseAndType(long id,String type);
	public PjPhaseProjet findByIdAlfresco (String idAlfresco);
}
