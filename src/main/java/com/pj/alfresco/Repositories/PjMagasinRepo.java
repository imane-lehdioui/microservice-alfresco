package com.pj.alfresco.Repositories;

import com.pj.alfresco.Models.PjMagasin;
import com.pj.alfresco.Models.PjPc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author R.SABRI Date de creation:02/04/20
 * 
 */
@Repository
public interface PjMagasinRepo extends JpaRepository<PjMagasin, Long> {
	List<PjMagasin> findByIdMagasinOrderByIdDesc(long idMagasin);

	List<PjMagasin> findByIdMagasinAndSousModuleOrderByIdDesc(long idMagasin, String sousModule);

	PjMagasin findByIdAlfresco(String idAlfresco);
}
