package com.pj.alfresco.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pj.alfresco.Models.PjAoRc;

@Repository
public interface PjAoRcRepo extends CrudRepository<PjAoRc, Long> {

}
