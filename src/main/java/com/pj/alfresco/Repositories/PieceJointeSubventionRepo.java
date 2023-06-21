package com.pj.alfresco.Repositories;

import com.pj.alfresco.Models.PieceJointeSubvention;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PieceJointeSubventionRepo  extends JpaRepository<PieceJointeSubvention, Long> {

    List<PieceJointeSubvention> findByIdSubventionOrderByIdDesc(long paramLong);
    List<PieceJointeSubvention> findByIdSubventionAndSousModuleOrderByIdDesc(long paramLong, String paramString);
    public PieceJointeSubvention findByIdAlfresco(String paramString);
}
