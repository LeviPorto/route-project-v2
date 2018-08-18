package com.levi.route.api.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi.route.api.entity.Coordinate;
import com.levi.route.api.repository.CoordinateRepository;
import com.levi.route.api.service.CoordinateService;

@Service
public class CoordinateService {
	
	@Autowired
	private CoordinateRepository coordinateRepository;

	private static final Logger log = LoggerFactory.getLogger(CoordinateService.class);

	public List<Coordinate> findLastTop2ByVehicle(Long vehicleId) {
		log.info("Finding coordinates");
		return this.coordinateRepository.findLastTop2ByVehicle(vehicleId);
	}
	
	public Coordinate persist(Coordinate coordinate) {
		log.info("Creating coordinate");
		return this.coordinateRepository.save(coordinate);
	}

}
