//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pj.alfresco.Repositories;

import com.pj.alfresco.Models.PJCourrie;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PJCourriersRepository extends JpaRepository<PJCourrie, Long> {
    List<PJCourrie> findByIdCourrierEntrantOrderByIdDesc(long idCourrierEntrant);

    PJCourrie findByIdAlfresco(String idAlfresco);

    @Modifying
    @Transactional
    @Query("delete  from PJCourrie c  where c.idCourrierEntrant=?1")
    void deleteByIdCourrierEntrant(long idCourrierEntrant);
}
