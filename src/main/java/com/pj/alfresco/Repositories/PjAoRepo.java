package com.pj.alfresco.Repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.pj.alfresco.Models.PjAo;

public interface PjAoRepo extends JpaRepository<PjAo, Long> {
	
	List<PjAo> findByIdAoOrderByIdDesc(long paramLong);
	
	List<PjAo> findByIdAoAndSousModuleOrderByIdDesc(long paramLong, String paramString);

	public PjAo findByIdAlfresco(String paramString);

	@Modifying
	@Transactional
	@Query("delete  from PjAo c  where c.type.id=?1")
	void deleteBytype(long paramLong);

}
