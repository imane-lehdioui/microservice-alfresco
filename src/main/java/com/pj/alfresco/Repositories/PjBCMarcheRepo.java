package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pj.alfresco.Models.PjBCMarche;

/**
 * 
 * @author R.SABRI on 05.01.2020
 *
 */
public interface PjBCMarcheRepo extends JpaRepository<PjBCMarche, Long> {

	public List<PjBCMarche> findByIdBCOrderByIdDesc(long id);

	public List<PjBCMarche> findByIdBCAndType(long id, String type);

	public PjBCMarche findOneByIdBCAndType(long id, String type);

	public PjBCMarche findByIdAlfresco(String idAlfresco);
}
