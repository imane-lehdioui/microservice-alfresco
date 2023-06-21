package com.pj.alfresco.Repositories;


import com.pj.alfresco.Models.PieceJointeAutorisation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PieceJointeAutorisationRepo extends JpaRepository<PieceJointeAutorisation, Long> {

    List<PieceJointeAutorisation> findByIdAutorisationOrderByIdDesc(long paramLong);

    List<PieceJointeAutorisation> findByIdAutorisationAndSousModuleOrderByIdDesc(long paramLong, String paramString);

    public PieceJointeAutorisation findByIdAlfresco(String paramString);

}
