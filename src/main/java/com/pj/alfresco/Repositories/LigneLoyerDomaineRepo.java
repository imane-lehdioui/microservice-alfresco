package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pj.alfresco.Models.PjLigneLoyerDomaine;


public interface LigneLoyerDomaineRepo extends JpaRepository<PjLigneLoyerDomaine, Long> {
	public List<PjLigneLoyerDomaine> findByIdLoyer(long id);
	public List<PjLigneLoyerDomaine> findByIdLoyerAndType(long id,String type);
	public PjLigneLoyerDomaine findOneByIdLoyerAndType(long id,String type);
	public PjLigneLoyerDomaine findByIdAlfresco (String idAlfresco);

}
