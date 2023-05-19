package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pj.alfresco.Models.PjPrestataire;

public interface PrestataireRepo extends JpaRepository<PjPrestataire, Long> {
	public List<PjPrestataire> findByIdPrestataire(long id);

	public List<PjPrestataire> findByIdPrestataireAndType(long id, String type);

	public PjPrestataire findOneByIdPrestataireAndType(long id, String type);

	public PjPrestataire findByIdAlfresco(String idAlfresco);
}
