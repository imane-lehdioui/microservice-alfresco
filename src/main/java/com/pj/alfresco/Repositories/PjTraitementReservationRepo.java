package com.pj.alfresco.Repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.pj.alfresco.Models.PjTraitementReservations;

public interface PjTraitementReservationRepo extends JpaRepository<PjTraitementReservations, Long> {
	// @Query("select c from PjTraitementReservations c where c.idReservation=?1")
	List<PjTraitementReservations> findByIdReservationOrderByIdDesc(long paramLong);

	// @Query("select c.name from PjTraitementReservations c where c.idAlfresco=?1")
	public PjTraitementReservations findByIdAlfresco(String paramString);

	@Modifying
	@Transactional
	@Query("delete  from  PjTraitementReservations c  where c.idReservation=?1")
	void deleteByIdReservation(long paramLong);
}
