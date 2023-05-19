package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pj.alfresco.Models.PjPublicite;

/**
 * @author R.SABRI Date de creation:10/02/20
 * 
 */
@Repository
public interface PjPubliciteRepo extends JpaRepository<PjPublicite, Long> {

	List<PjPublicite> findByIdPubliciteOrderByIdDesc(long idPublicite);

	public PjPublicite findByIdAlfresco(String idAlfresco);

}
