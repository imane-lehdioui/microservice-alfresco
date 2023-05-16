package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.pj.alfresco.Models.PjMagasinPhotos;

public interface MagasinPhotosRepo extends JpaRepository<PjMagasinPhotos, Long> {

	public List<PjMagasinPhotos> findByIdmagasin(long id);

	public PjMagasinPhotos findByIdAlfresco (String idAlfresco);
}
