package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pj.alfresco.Models.PjActivite;

/**
 * @author R.SABRI Date de creation:02/04/20
 * 
 */
@Repository
public interface PjActiviteRepo extends JpaRepository<PjActivite, Long> {

	List<PjActivite> findByIdActiviteOrderByIdDesc(long idActivite);

	public PjActivite findByIdAlfresco(String idAlfresco);


}
