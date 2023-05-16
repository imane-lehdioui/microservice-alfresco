package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pj.alfresco.Models.PjApptPhotos;



public interface ApptPhotoRepo extends JpaRepository<PjApptPhotos, Long> {
	public List<PjApptPhotos> findByIdappartement(long id);

	public PjApptPhotos findByIdAlfresco (String idAlfresco);
}
