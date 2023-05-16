package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pj.alfresco.Models.PjPartagesUsers;

public interface PjPartagesUsersRepo extends CrudRepository<PjPartagesUsers, Long> {
	@Query("select c from PjPartagesUsers c where c.idUser=?1 order by c.id desc")
	List<PjPartagesUsers> findByIdUser(long paramLong);

	@Query("select c.name from PjPartagesUsers c where c.idAlfresco=?1")
	String findByIdPj(String paramString);

	List<PjPartagesUsers> findAllByOrderByIdDesc();
}
