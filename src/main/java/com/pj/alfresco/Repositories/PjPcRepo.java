package com.pj.alfresco.Repositories;

import com.pj.alfresco.Models.PjConvention;
import com.pj.alfresco.Models.PjPc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author R.SABRI Date de creation:02/04/20
 * 
 */
@Repository
public interface PjPcRepo extends JpaRepository<PjPc, Long> {
	List <PjPc> findByIdPcOrderByIdDesc(long idPc);

	List <PjPc> findByIdPcAndSousModuleOrderByIdDesc(long idPc,String paramString);

	PjPc findByIdAlfresco(String idAlfresco);


}
