package com.pj.alfresco.Repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pj.alfresco.Models.PjCourriersSortants;

/**
 * @author R.SABRI Date de creation:10/02/20
 * 
 */
@Repository
public interface PjCourriersSortantsRepo extends JpaRepository<PjCourriersSortants, Long> {

	List<PjCourriersSortants> findByIdCourrierSortantOrderByIdDesc(long idCourrierSortant);

	public PjCourriersSortants findByIdAlfresco(String idAlfresco);

	@Modifying
	@Transactional
	@Query("delete  from PjCourriersSortants c  where c.idCourrierSortant=?1")
	void deleteByIdCourrierSortant(long idCourrierSortant);

}
