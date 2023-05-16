package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.pj.alfresco.Models.PjLmarchePhotos;

public interface LmarchePhotosRepo extends JpaRepository<PjLmarchePhotos, Long> {
	public List<PjLmarchePhotos> findByIdmarche(long id);

	public PjLmarchePhotos findByIdAlfresco (String idAlfresco);
}
