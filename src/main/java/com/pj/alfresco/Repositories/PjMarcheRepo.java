package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pj.alfresco.Models.PjMarche;

/**
 * 
 * @author R.SABRI on 05.01.2020
 *
 */
public interface PjMarcheRepo extends JpaRepository<PjMarche, Long> {

	public List<PjMarche> findByIdMarcheOrderByIdDesc(long id);

	public List<PjMarche> findByIdMarcheAndType(long id, String type);

	public PjMarche findOneByIdMarcheAndType(long id, String type);

	public PjMarche findByIdAlfresco(String idAlfresco);
}
