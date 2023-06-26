package com.pj.alfresco.Repositories;


import com.pj.alfresco.Models.ConvnetionMarche;
import com.pj.alfresco.Models.ProgrammeMarche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgrammeMarcheRepo extends JpaRepository<ProgrammeMarche, Long> {
	
	List<ProgrammeMarche> findByIdProgrammeOrderByIdDesc(long paramLong);

	List<ProgrammeMarche> findByIdProgrammeAndSousModuleOrderByIdDesc(long paramLong, String sousModule);

	public ProgrammeMarche findByIdAlfresco(String paramString);



}
