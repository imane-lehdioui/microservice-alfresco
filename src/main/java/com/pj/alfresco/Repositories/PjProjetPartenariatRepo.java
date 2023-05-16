package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pj.alfresco.Models.PjProjetPartenariat;

/**
 * @author R.SABRI Date de creation:02/04/20
 * 
 */
@Repository
public interface PjProjetPartenariatRepo extends JpaRepository<PjProjetPartenariat, Long> {

	List<PjProjetPartenariat> findByIdProjetPartenariatOrderByIdDesc(long idProjet);

	public PjProjetPartenariat findByIdAlfresco(String idAlfresco);

}
