package com.ngen.cosys.platform.rfid.tracker.interfaces.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AwbModel {

	private String awbNo;
	private String hawbNo;
	private Integer totalPcs;
	private String flightNumber;
	private String flightDate;
	private String flightKey;
	private String carrier;
	private String origin;
	private String station;
	private String destination;
	private String SHC_1;
	private String SHC_2;
	private String SHC_3;
	private String SHC_4;
	private String SHC_5;
	private String SHC_6;
	private String SHC_7;
	private String SHC_8;
	private String SHC_9;
	private String weight;
	private String weightUnit;
	private String shipmentType;
	private Integer assignedTotalPcs;
	private String flowType;
	private String transitType;
	private String created_DateTime;
	private String lastUpdated_DateTime;
	private String createdUser;
	private String lastUpdated_User;
	private String stage;
	private String assignedTotalWt;
	private String SHCS;
	
	private String brdPoint;
	private String offPoint;
	private String totalWt;

	@JsonIgnore
	private String flightKeyDate;
	
	@JsonIgnore
	private String flightEstimatedDate;
	
	@JsonIgnore
	private String flightActualDate;
	
	@JsonIgnore
	private String impFlightKey;
	
	@JsonIgnore
	private String impFlightKeyDate;
	
	@JsonIgnore
	private String expFlightKey;
	 
	@JsonIgnore
	private String expFlightKeyDate;
		

	
	public String getImpFlightKey() {
		return impFlightKey;
	}

	public void setImpFlightKey(String impFlightKey) {
		this.impFlightKey = impFlightKey;
	}

	public String getImpFlightKeyDate() {
		return impFlightKeyDate;
	}

	public void setImpFlightKeyDate(String impFlightKeyDate) {
		this.impFlightKeyDate = impFlightKeyDate;
	}

	public String getExpFlightKey() {
		return expFlightKey;
	}

	public void setExpFlightKey(String expFlightKey) {
		this.expFlightKey = expFlightKey;
	}

	public String getExpFlightKeyDate() {
		return expFlightKeyDate;
	}

	public void setExpFlightKeyDate(String expFlightKeyDate) {
		this.expFlightKeyDate = expFlightKeyDate;
	}

	public String getBrdPoint() {
		return brdPoint;
	}

	public void setBrdPoint(String brdPoint) {
		this.brdPoint = brdPoint;
	}

	public String getOffPoint() {
		return offPoint;
	}

	public void setOffPoint(String offPoint) {
		this.offPoint = offPoint;
	}

	private List<ULDModel> ulds;

	private List<HawbModel> hawbNos;

	public String getAwbNo() {
		return awbNo;
	}

	public void setAwbNo(String awbNo) {
		this.awbNo = awbNo;
	}

	public String getHawbNo() {
		return hawbNo;
	}

	public void setHawbNo(String hawbNo) {
		this.hawbNo = hawbNo;
	}

	public Integer getTotalPcs() {
		return totalPcs;
	}

	public void setTotalPcs(Integer totalPcs) {
		this.totalPcs = totalPcs;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	public String getFlightKey() {
		return flightKey;
	}

	public void setFlightKey(String flightKey) {
		this.flightKey = flightKey;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getSHC_1() {
		return SHC_1;
	}

	public void setSHC_1(String sHC_1) {
		SHC_1 = sHC_1;
	}

	public String getSHC_2() {
		return SHC_2;
	}

	public void setSHC_2(String sHC_2) {
		SHC_2 = sHC_2;
	}

	public String getSHC_3() {
		return SHC_3;
	}

	public void setSHC_3(String sHC_3) {
		SHC_3 = sHC_3;
	}

	public String getSHC_4() {
		return SHC_4;
	}

	public void setSHC_4(String sHC_4) {
		SHC_4 = sHC_4;
	}

	public String getSHC_5() {
		return SHC_5;
	}

	public void setSHC_5(String sHC_5) {
		SHC_5 = sHC_5;
	}

	public String getSHC_6() {
		return SHC_6;
	}

	public void setSHC_6(String sHC_6) {
		SHC_6 = sHC_6;
	}

	public String getSHC_7() {
		return SHC_7;
	}

	public void setSHC_7(String sHC_7) {
		SHC_7 = sHC_7;
	}

	public String getSHC_8() {
		return SHC_8;
	}

	public void setSHC_8(String sHC_8) {
		SHC_8 = sHC_8;
	}

	public String getSHC_9() {
		return SHC_9;
	}

	public void setSHC_9(String sHC_9) {
		SHC_9 = sHC_9;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public String getShipmentType() {
		return shipmentType;
	}

	public void setShipmentType(String shipmentType) {
		this.shipmentType = shipmentType;
	}

	public Integer getAssignedTotalPcs() {
		return assignedTotalPcs;
	}

	public void setAssignedTotalPcs(Integer assignedTotalPcs) {
		this.assignedTotalPcs = assignedTotalPcs;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public String getTransitType() {
		return transitType;
	}

	public void setTransitType(String transitType) {
		this.transitType = transitType;
	}

	public String getCreated_DateTime() {
		return created_DateTime;
	}

	public void setCreated_DateTime(String created_DateTime) {
		this.created_DateTime = created_DateTime;
	}

	public String getLastUpdated_DateTime() {
		return lastUpdated_DateTime;
	}

	public void setLastUpdated_DateTime(String lastUpdated_DateTime) {
		this.lastUpdated_DateTime = lastUpdated_DateTime;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getLastUpdated_User() {
		return lastUpdated_User;
	}

	public void setLastUpdated_User(String lastUpdated_User) {
		this.lastUpdated_User = lastUpdated_User;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getAssignedTotalWt() {
		return assignedTotalWt;
	}

	public void setAssignedTotalWt(String assignedTotalWt) {
		this.assignedTotalWt = assignedTotalWt;
	}

	public String getFlightKeyDate() {
		return flightKeyDate;
	}

	public void setFlightKeyDate(String flightKeyDate) {
		this.flightKeyDate = flightKeyDate;
	}

	public List<ULDModel> getUlds() {
		return ulds;
	}

	public void setUlds(List<ULDModel> ulds) {
		this.ulds = ulds;
	}

	public List<HawbModel> getHawbNos() {
		return hawbNos;
	}

	public void setHawbNos(List<HawbModel> hawbNos) {
		this.hawbNos = hawbNos;
	}

	public String getSHCS() {
		return SHCS;
	}

	public void setSHCS(String sHCS) {
		SHCS = sHCS;
	}

	public String getFlightEstimatedDate() {
		return flightEstimatedDate;
	}

	public void setFlightEstimatedDate(String flightEstimatedDate) {
		this.flightEstimatedDate = flightEstimatedDate;
	}

	public String getFlightActualDate() {
		return flightActualDate;
	}

	public void setFlightActualDate(String flightActualDate) {
		this.flightActualDate = flightActualDate;
	}

	public String getTotalWt() {
		return totalWt;
	}

	public void setTotalWt(String totalWt) {
		this.totalWt = totalWt;
	}

	
}
