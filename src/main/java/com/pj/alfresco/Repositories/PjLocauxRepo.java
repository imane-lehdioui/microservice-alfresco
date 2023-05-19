package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pj.alfresco.Models.PjLocaux;

/**
 * @author R.SABRI Date de creation:02/04/20
 * 
 */
@Repository
public interface PjLocauxRepo extends JpaRepository<PjLocaux, Long> {

	List<PjLocaux> findByIdLocauxOrderByIdDesc(long idLocaux);

	public PjLocaux findByIdAlfresco(String idAlfresco);

}
