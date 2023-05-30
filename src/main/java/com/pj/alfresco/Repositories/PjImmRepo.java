package com.pj.alfresco.Repositories;

import com.pj.alfresco.Models.PjImm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PjImmRepo extends JpaRepository<PjImm, Long> {


}
