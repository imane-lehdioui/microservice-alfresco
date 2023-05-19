package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.pj.alfresco.Models.PjToilette;



public interface ToiletteRepo extends JpaRepository<PjToilette, Long>{
	public List<PjToilette> findByIdToilette(long id);
	public List<PjToilette> findByIdToiletteAndType(long id,String type);
	public PjToilette findOneByIdToiletteAndType(long id,String type);
	public PjToilette findByIdAlfresco (String idAlfresco);
}
