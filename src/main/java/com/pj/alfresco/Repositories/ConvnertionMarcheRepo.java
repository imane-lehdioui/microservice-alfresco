//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pj.alfresco.Repositories;

import com.pj.alfresco.Models.ConvnetionMarche;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConvnertionMarcheRepo extends JpaRepository<ConvnetionMarche, Long> {
    List<ConvnetionMarche> findByIdConventionOrderByIdDesc(long paramLong);

    List<ConvnetionMarche> findByIdConventionAndSousModuleOrderByIdDesc(long paramLong, String paramString);

    ConvnetionMarche findByIdAlfresco(String paramString);
}
