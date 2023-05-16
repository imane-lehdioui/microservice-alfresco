package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pj.alfresco.Models.PjInterventionRapide;

/**
 * @author R.SABRI Date de creation:08/04/20
 * 
 */
@Repository
public interface PjInterventionRapideRepo extends JpaRepository<PjInterventionRapide, Long> {

	public List<PjInterventionRapide> findByIdInterventionRapideOrderByIdDesc(long idIntervention);

	public PjInterventionRapide findByIdAlfresco(String idAlfresco);

}
