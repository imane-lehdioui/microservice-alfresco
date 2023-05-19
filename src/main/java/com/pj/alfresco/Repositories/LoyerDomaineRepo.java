package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pj.alfresco.Models.PjLoyerDomaine;

public interface LoyerDomaineRepo extends JpaRepository<PjLoyerDomaine, Long> {
	public List<PjLoyerDomaine> findByIdLoyer(long id);

	public List<PjLoyerDomaine> findByIdLoyerAndType(long id, String type);

	public PjLoyerDomaine findOneByIdLoyerAndType(long id, String type);

	public PjLoyerDomaine findByIdAlfresco(String idAlfresco);

}
