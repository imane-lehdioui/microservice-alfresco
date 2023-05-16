package com.pj.alfresco.Repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pj.alfresco.Models.PjReclamations;

@Repository
public interface PjReclamationRepo extends CrudRepository<PjReclamations, Long> {
	@Query(value = "select c from PjReclamations c where c.idReclamation=?1")
	List<PjReclamations> findByIdReclamation(long paramLong);

	@Query(value = "select c.name from PjReclamations c where c.idAlfresco=?1")
	String findByIdPj(String paramString);

	@Modifying
	@Transactional
	@Query("delete  from PjReclamations c  where c.idReclamation=?1")
	void deleteByIdReclamation(long paramLong);
}
