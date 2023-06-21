package com.pj.alfresco.Repositories;


import com.pj.alfresco.Models.PieceJointeLogistique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PieceJointeLogistiqueRepo  extends JpaRepository<PieceJointeLogistique, Long> {

    List<PieceJointeLogistique> findByIdLogistiqueOrderByIdDesc(long paramLong);

    List<PieceJointeLogistique> findByIdLogistiqueAndSousModuleOrderByIdDesc(long paramLong, String paramString);

    public PieceJointeLogistique findByIdAlfresco(String paramString);

}
