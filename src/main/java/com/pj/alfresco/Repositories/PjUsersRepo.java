package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pj.alfresco.Models.PjUsers;

@Repository
public interface PjUsersRepo extends CrudRepository<PjUsers, Long> {
	@Query(value = "select c from PjUsers c where c.idUser=?1 order by c.id desc")
	List<PjUsers> findByIdUser(long paramLong);

	@Query(value = "select c.name from PjUsers c where c.idAlfresco=?1")
	String findByIdPj(String paramString);

	List<PjUsers> findAllByOrderByIdDesc();
}
