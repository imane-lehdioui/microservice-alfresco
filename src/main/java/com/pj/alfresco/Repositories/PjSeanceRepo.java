package com.pj.alfresco.Repositories;

import com.pj.alfresco.Models.PjPc;
import com.pj.alfresco.Models.PjSeance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author R.SABRI Date de creation:02/04/20
 * 
 */
@Repository
public interface PjSeanceRepo extends JpaRepository<PjSeance, Long> {
	List<PjSeance> findByIdSeanceAndSousModuleOrderByIdDesc(long idSeance, String sousModule);

	List <PjSeance> findByIdSeanceOrderByIdDesc(long idSeance);

	PjSeance findByIdAlfresco(String idAlfresco);
}
