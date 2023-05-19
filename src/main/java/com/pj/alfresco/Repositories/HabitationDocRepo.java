package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pj.alfresco.Models.PjHabitatinDoc;



public interface HabitationDocRepo extends JpaRepository<PjHabitatinDoc, Long> {
	public List<PjHabitatinDoc> findByIdhabitation(long id);
	
	public PjHabitatinDoc findByIdAlfresco (String idAlfresco);
}
