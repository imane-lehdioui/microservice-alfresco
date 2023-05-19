package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pj.alfresco.Models.PjProjet;


public interface ProjetRepo extends JpaRepository<PjProjet, Long> {
	public List<PjProjet> findByIdProjet(long id);
	public List<PjProjet> findByIdProjetAndType(long id,String type);
	public PjProjet findOneByIdProjetAndType(long id,String type);
	public PjProjet findByIdAlfresco (String idAlfresco);
}
