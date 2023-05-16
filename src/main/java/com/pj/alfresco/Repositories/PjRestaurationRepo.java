package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pj.alfresco.Models.PjRestauration;

/**
 * @author R.SABRI Date de creation:02/04/20
 * 
 */
@Repository
public interface PjRestaurationRepo extends JpaRepository<PjRestauration, Long> {

	List<PjRestauration> findByIdRestaurationOrderByIdDesc(long idResto);

	public PjRestauration findByIdAlfresco(String idAlfresco);

}
