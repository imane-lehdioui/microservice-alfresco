package com.pj.alfresco.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.pj.alfresco.Models.TypePjAo;
import org.springframework.stereotype.Repository;

@Repository
public interface TypePjAoRepo extends CrudRepository<TypePjAo, Long> {

}
