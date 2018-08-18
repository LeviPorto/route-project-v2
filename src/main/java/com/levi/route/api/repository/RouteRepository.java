package com.levi.route.api.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.levi.route.api.entity.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {

	@Query("SELECT r FROM Route r WHERE (r.status = 'PENDING' OR r.status = 'PROGRESS')")
	List<Route> findPendingOrProgress();
	
	@Query(value = "SELECT CASE" + 
			"			WHEN STR_TO_DATE(:date, '%Y-%m-%d %h:%i:%s') < r.update_status_progress_date OR r.update_status_progress_date IS NULL THEN 'PENDING'" + 
			"   		WHEN (STR_TO_DATE(:date, '%Y-%m-%d %h:%i:%s') >= r.update_status_progress_date AND STR_TO_DATE(:date, '%Y-%m-%d %h:%i:%s') < r.update_status_finished_date) OR (r.update_status_finished_date IS NULL) THEN 'PROGRESS'" + 
			"   		WHEN STR_TO_DATE(:date, '%Y-%m-%d %h:%i:%s') >= r.update_status_finished_date THEN 'FINISHED'" + 
			"		END AS status " + 
			"		from route AS r WHERE r.id = :route_id", nativeQuery = true)
	String findStatusInDate(@Param("date") Date date, @Param("route_id") Long routeId);
	
	
	
}
