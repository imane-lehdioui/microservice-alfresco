//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pj.alfresco.Repositories;

import com.pj.alfresco.Models.ImmPJ;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PJImmRepository extends JpaRepository<ImmPJ, Long> {
    List<ImmPJ> findByIdImmOrderByIdDesc(long paramLong);

    List<ImmPJ> findByIdImmAndSousModuleOrderByIdDesc(long paramLong, String paramString);

    ImmPJ findByIdAlfresco(String paramString);

    @Modifying
    @Transactional
    @Query("delete  from ImmPJ c  where c.id=?1")
    void delete(long paramLong);
}
