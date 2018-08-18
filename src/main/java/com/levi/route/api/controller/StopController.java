package com.levi.route.api.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi.route.api.dto.StopDto;
import com.levi.route.api.entity.Stop;
import com.levi.route.api.enun.StopStatus;
import com.levi.route.api.response.Response;
import com.levi.route.api.service.StopService;


@RestController
@RequestMapping("/route_processor/stop")
@CrossOrigin(origins = "*")
public class StopController {

	private static final Logger log = LoggerFactory.getLogger(StopController.class);
	
	@Autowired
	private StopService stopService;
	
	public StopController() {
		
	}
	
	@PostMapping
	public ResponseEntity<Response<StopDto>> create(@Valid @RequestBody StopDto stopDto,
			BindingResult result) throws ParseException {
		log.info("Add stop: {}", stopDto.toString());
		Response<StopDto> response = new Response<StopDto>();
		
		Stop stop = this.convertDtoToStop(stopDto, result);

		if(stop.getStopStatus() == null) {
			stop.setStopStatus(StopStatus.PENDING);
		}
		stop = this.stopService.persist(stop);
		response.setData(this.convertStopToDto(stop));
		return ResponseEntity.ok(response);
	}
	
	@PostMapping(value = "/finishedStopByRoute")
	public ResponseEntity<Response<List<StopDto>>> findFinishedStopByRoute(@Valid
			@RequestBody String date)  {
		
		log.info("Calculating finished stops by route in: {}", date);
		Response<List<StopDto>> response = new Response<List<StopDto>>();
		
	    List<Stop> stops = stopService.findFinishedStopByRoute(date);
	    List<StopDto> stopDtos = new ArrayList<>();
	    
	    for(int i = 0;i<stops.size();++i) {
	    	stopDtos.add(convertStopToDto(stops.get(i)));
	    }
	    
	    response.setData(stopDtos);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remove(@PathVariable("id") Long id) {
		log.info("Removing stop ID: {}", id);
		this.stopService.remove(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	@GetMapping(value = "/LongerStopByRoute")
	public ResponseEntity<Response<List<StopDto>>> findLongerStopByRoute()  {
		
		log.info("Finding longer stop by route {}");
		Response<List<StopDto>> response = new Response<List<StopDto>>();
		
		
		List<Stop> stops = stopService.findLongerStopByRoute();
		List<StopDto> stopDtos = new ArrayList<>();
		
	    for(int i = 0;i<stops.size();++i) {
	    	stopDtos.add(convertStopToDto(stops.get(i)));
	    }
		
	    response.setData(stopDtos);
		return ResponseEntity.ok(response);
	}
	
	private Stop convertDtoToStop(StopDto stopDto, BindingResult result) throws ParseException {
		Stop stop = new Stop();
		
		stop.getRoute().setId(Long.valueOf(stopDto.getRouteId()));
		stop.setLat(Double.valueOf(stopDto.getLat()));
		stop.setLng(Double.valueOf(stopDto.getLng()));
		stop.setDescription(stopDto.getDescription());
		stop.setDeliveryRadius(Double.valueOf(stopDto.getDeliveryRadius()));
		stop.setStopStatus(StopStatus.valueOf(stopDto.getStopStatus()));
		
		return stop;
	}
	
	private StopDto convertStopToDto(Stop stop) {
		StopDto stopDto = new StopDto();
		
		stopDto.setId(stop.getId());
		stopDto.setLat(String.valueOf(stop.getLat()));
		stopDto.setLng(String.valueOf(stop.getLng()));
		stopDto.setDescription(stop.getDescription());
		stopDto.setDeliveryRadius(String.valueOf(stop.getDeliveryRadius()));
		stopDto.setRouteId(String.valueOf(stop.getRoute().getId()));
		stopDto.setStopStatus(String.valueOf(stop.getStopStatus()));
		
		
		return stopDto;
	}
}