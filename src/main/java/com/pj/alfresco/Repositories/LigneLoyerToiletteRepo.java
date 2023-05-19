package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pj.alfresco.Models.PjLigneLoyerToilette;



public interface LigneLoyerToiletteRepo extends JpaRepository<PjLigneLoyerToilette, Long> {
	public List<PjLigneLoyerToilette> findByIdLoyer(long id);
	public List<PjLigneLoyerToilette> findByIdLoyerAndType(long id,String type);
	public PjLigneLoyerToilette findOneByIdLoyerAndType(long id,String type);
	public PjLigneLoyerToilette findByIdAlfresco (String idAlfresco);
}
