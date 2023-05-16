package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pj.alfresco.Models.PjHabitationPhotos;



public interface HabitationPhotoRepo extends JpaRepository<PjHabitationPhotos, Long> {
	public List<PjHabitationPhotos> findByIdhabitation(long id);

	public PjHabitationPhotos findByIdAlfresco (String idAlfresco);
}
