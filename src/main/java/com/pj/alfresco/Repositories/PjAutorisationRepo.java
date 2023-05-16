package com.pj.alfresco.Repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.pj.alfresco.Models.pjAutorisations;

public interface PjAutorisationRepo extends JpaRepository<pjAutorisations, Long> {
	// @Query("select c from pjAutorisations c where c.idAutorisation=?1")
	List<pjAutorisations> findByIdAutorisationOrderByIdDesc(long paramLong);

	// @Query("select c.name from pjAutorisations c where c.idAlfresco=?1")
	public pjAutorisations findByIdAlfresco(String paramString);

	@Modifying
	@Transactional
	@Query("delete  from  pjAutorisations c  where c.idAutorisation=?1")
	void deleteByIdAutorisation(long paramLong);
}
