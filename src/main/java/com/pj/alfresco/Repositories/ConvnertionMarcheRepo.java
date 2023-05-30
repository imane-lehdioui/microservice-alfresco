package com.pj.alfresco.Repositories;


import com.pj.alfresco.Models.ConvnetionMarche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ConvnertionMarcheRepo extends JpaRepository<ConvnetionMarche, Long> {
	
	List<ConvnetionMarche> findByIdConventionOrderByIdDesc(long paramLong);
	
	List<ConvnetionMarche> findByIdConventionAndSousModuleOrderByIdDesc(long paramLong, String paramString);

	public ConvnetionMarche findByIdAlfresco(String paramString);



}
