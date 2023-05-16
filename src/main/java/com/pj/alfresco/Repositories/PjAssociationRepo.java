package com.pj.alfresco.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pj.alfresco.Models.PjAssociation;

/**
 * @author R.SABRI Date de creation:02/04/20
 * 
 */
@Repository
public interface PjAssociationRepo extends JpaRepository<PjAssociation, Long> {

	List<PjAssociation> findByIdAssociationOrderByIdDesc(long idAssociation);

	public PjAssociation findByIdAlfresco(String idAlfresco);

}
