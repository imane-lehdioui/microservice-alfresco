package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pj.alfresco.Models.PjLoyerToilette;


public interface LoyerToiletteRepo extends JpaRepository<PjLoyerToilette, Long> {
	public List<PjLoyerToilette> findByIdLoyer(long id);
	public List<PjLoyerToilette> findByIdLoyerAndType(long id,String type);
	public PjLoyerToilette findOneByIdLoyerAndType(long id,String type);
	public PjLoyerToilette findByIdAlfresco (String idAlfresco);
}
