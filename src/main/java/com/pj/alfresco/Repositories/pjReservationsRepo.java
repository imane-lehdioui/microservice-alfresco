package com.pj.alfresco.Repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.pj.alfresco.Models.PjReservations;

public interface pjReservationsRepo extends JpaRepository<PjReservations, Long> {
	//@Query("select c from PjReservations c where c.idReservation=?1")
	List<PjReservations> findByIdReservationOrderByIdDesc(long paramLong);

	//@Query("select c.name from  PjReservations c where c.idAlfresco=?1")
	public PjReservations findByIdAlfresco(String paramString);

	@Modifying
	@Transactional
	@Query("delete  from  PjReservations c  where c.idReservation=?1")
	void deleteByIdReservation(long paramLong);
}
