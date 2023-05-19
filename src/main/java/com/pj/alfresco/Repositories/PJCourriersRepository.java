package com.pj.alfresco.Repositories;

import com.pj.alfresco.Models.PJCourrie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PJCourriersRepository extends JpaRepository<PJCourrie, Long> {
  List<PJCourrie> findByIdCourrierEntrantOrderByIdDesc(long idCourrierEntrant);

  public PJCourrie findByIdAlfresco(String idAlfresco);

  @Modifying
  @Transactional
  @Query("delete  from PJCourrie c  where c.idCourrierEntrant=?1")
  void deleteByIdCourrierEntrant(long idCourrierEntrant);
}
