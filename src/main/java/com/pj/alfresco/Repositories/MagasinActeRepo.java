package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.pj.alfresco.Models.PjMagasinActe;

public interface MagasinActeRepo extends JpaRepository<PjMagasinActe, Long> {
	public List<PjMagasinActe> findByIdloyer(long id);

	public PjMagasinActe findByIdAlfresco (String idAlfresco);
}
