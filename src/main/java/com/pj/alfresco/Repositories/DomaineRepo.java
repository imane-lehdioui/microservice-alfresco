package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pj.alfresco.Models.PjDomaine;



public interface DomaineRepo extends JpaRepository<PjDomaine, Long> {
	
	public List<PjDomaine> findByIdDomaine(long id);
	public List<PjDomaine> findByIdDomaineAndType(long id,String type);
	public PjDomaine findOneByIdDomaineAndType(long id,String type);
	public PjDomaine findByIdAlfresco (String idAlfresco);

}
