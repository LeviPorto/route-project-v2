package com.levi.route.api.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.levi.route.api.dto.RouteDto;
import com.levi.route.api.enun.RouteStatus;

@Entity
@Table(name = "route")
public class Route {
	
	private Long id;
	private RouteStatus status;
	private Long assignedVehicle;
	private List<Stop> plannedStops;
	private String routePlan;
	private Date startDate;
	private Date endDate;
	
	public Route() {
		
	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "assigned_vehicle", nullable = true)
	public Long getAssignedVehicle() {
		return assignedVehicle;
	}

	public void setAssignedVehicle(Long assignedVehicle) {
		this.assignedVehicle = assignedVehicle;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "route_status", nullable = false)
	public RouteStatus getStatus() {
		return status;
	}

	public void setStatus(RouteStatus status) {
		this.status = status;
	}

	@OneToMany(mappedBy = "route", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("route")
	public List<Stop> getPlannedStops() {
		return plannedStops;
	}

	public void setPlannedStops(List<Stop> plannedStops) {
		this.plannedStops = plannedStops;
	}

	@Column(name = "route_plan", nullable = false)
	public String getRoutePlan() {
		return routePlan;
	}

	public void setRoutePlan(String routePlan) {
		this.routePlan = routePlan;
	}

	@Column(name = "start_date", nullable = true)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "end_date", nullable = true)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public static Route fromDto(RouteDto routeDto) {
		Route route = new Route();
		
		route.setAssignedVehicle(Long.valueOf(routeDto.getAssignedVehicle()));
		route.setRoutePlan(routeDto.getRoutePlan());
		route.setPlannedStops(routeDto.getPlannedStops());
		
		return route;
	}
	
}
