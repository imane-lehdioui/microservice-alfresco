package com.pj.alfresco.Repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.pj.alfresco.Models.pjAutorisations;
import com.pj.alfresco.Models.pjTraitementAutorisations;

public interface PjTraitementAutorisationRepo extends JpaRepository<pjTraitementAutorisations, Long> {
	// @Query("select c from pjTraitementAutorisations c where c.idAutorisation=?1")
	List<pjTraitementAutorisations> findByIdAutorisationOrderByIdDesc(long paramLong);

	// @Query("select c.name from pjTraitementAutorisations c where
	// c.idAlfresco=?1")
	public pjAutorisations findByIdAlfresco(String paramString);

	@Modifying
	@Transactional
	@Query("delete  from  pjTraitementAutorisations c  where c.idAutorisation=?1")
	void deleteByIdAutorisation(long paramLong);
}
