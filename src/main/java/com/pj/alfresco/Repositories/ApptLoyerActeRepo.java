package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pj.alfresco.Models.PjApptLoyerActe;


public interface ApptLoyerActeRepo extends JpaRepository<PjApptLoyerActe, Long> {
	public List<PjApptLoyerActe> findByIdloyer(long id);

	public PjApptLoyerActe findByIdAlfresco (String idAlfresco);
}
