package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pj.alfresco.Models.PjImpression;

/**
 * @author R.SABRI Date de creation:02/04/20
 * 
 */
@Repository
public interface PjImpressionRepo extends JpaRepository<PjImpression, Long> {

	List<PjImpression> findByIdImpressionOrderByIdDesc(long idImpression);

	public PjImpression findByIdAlfresco(String idAlfresco);

}
