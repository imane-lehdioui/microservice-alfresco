package com.pj.alfresco.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pj.alfresco.Models.PjAoCps;

@Repository
public interface PjAoCpsRepo extends CrudRepository<PjAoCps, Long> {

}
