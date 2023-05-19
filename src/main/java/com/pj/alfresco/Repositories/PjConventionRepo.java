package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pj.alfresco.Models.PjConvention;

/**
 * @author R.SABRI Date de creation:02/04/20
 * 
 */
@Repository
public interface PjConventionRepo extends JpaRepository<PjConvention, Long> {

	List<PjConvention> findByIdConventionOrderByIdDesc(long id);

	public PjConvention findByIdAlfresco(String idAlfresco);
}
