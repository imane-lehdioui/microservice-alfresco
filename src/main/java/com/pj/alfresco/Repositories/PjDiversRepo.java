package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pj.alfresco.Models.PjDivers;

/**
 * @author R.SABRI Date de creation:09/08/20
 * 
 */
@Repository
public interface PjDiversRepo extends JpaRepository<PjDivers, Long> {

	List<PjDivers> findByIdDiversOrderByIdDesc(long idDivers);

	public PjDivers findByIdAlfresco(String idAlfresco);

}
