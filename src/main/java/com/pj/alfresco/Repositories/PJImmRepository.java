package com.pj.alfresco.Repositories;

import com.pj.alfresco.Models.ImmPJ;
import com.pj.alfresco.Models.PjAo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository
public interface PJImmRepository extends JpaRepository<ImmPJ, Long> {

    List<ImmPJ> findByIdImmOrderByIdDesc(long paramLong);

    List<ImmPJ> findByIdImmAndSousModuleOrderByIdDesc(long paramLong, String paramString);

    public ImmPJ findByIdAlfresco(String paramString);

    @Modifying
    @Transactional
    @Query("delete  from ImmPJ c  where c.id=?1")
    void delete(long paramLong);
}
