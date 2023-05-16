package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pj.alfresco.Models.PjSubvention;

/**
 * @author R.SABRI Date de creation:02/04/20
 * 
 */
@Repository
public interface PjSubventionRepo extends JpaRepository<PjSubvention, Long> {

	List<PjSubvention> findByIdSubventionOrderByIdDesc(long idSubvention);

	public PjSubvention findByIdAlfresco(String idAlfresco);

}
