package com.pj.alfresco.Repositories;

import com.pj.alfresco.Models.ConvnetionMarche;
import com.pj.alfresco.Models.PieceJointeConvention;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PieceJointeConventionRepo extends JpaRepository<PieceJointeConvention, Long> {

    List<PieceJointeConvention> findByIdConventionOrderByIdDesc(long paramLong);

    List<PieceJointeConvention> findByIdConventionAndSousModuleOrderByIdDesc(long paramLong, String paramString);

    public PieceJointeConvention findByIdAlfresco(String paramString);

}
