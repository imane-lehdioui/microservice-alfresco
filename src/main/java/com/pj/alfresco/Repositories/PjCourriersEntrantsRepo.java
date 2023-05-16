package com.pj.alfresco.Repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pj.alfresco.Models.PjCourriersEntrants;

/**
 * @author R.SABRI Date de creation:10/02/20
 * 
 */
@Repository
public interface PjCourriersEntrantsRepo extends JpaRepository<PjCourriersEntrants, Long> {

	List<PjCourriersEntrants> findByIdCourrierEntrantOrderByIdDesc(long idCourrierEntrant);

	public PjCourriersEntrants findByIdAlfresco(String idAlfresco);

	@Modifying
	@Transactional
	@Query("delete  from PjCourriersEntrants c  where c.idCourrierEntrant=?1")
	void deleteByIdCourrierEntrant(long idCourrierEntrant);

}
