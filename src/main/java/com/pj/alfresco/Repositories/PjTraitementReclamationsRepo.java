package com.pj.alfresco.Repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pj.alfresco.Models.PjTraitementReclamations;

public interface PjTraitementReclamationsRepo extends CrudRepository<PjTraitementReclamations, Long> {
	@Query(value = "select c from PjTraitementReclamations c where c.idReclamation=?1")
	List<PjTraitementReclamations> findByIdReclamation(long paramLong);

	@Query("select c.name from  PjTraitementReclamations c where c.idAlfresco=?1")
	String findByIdPj(String paramString);

	@Modifying
	@Transactional
	@Query("delete  from  PjTraitementReclamations c  where c.idReclamation=?1")
	void deleteByIdReclamation(long paramLong);
}
