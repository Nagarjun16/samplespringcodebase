package com.ngen.cosys.platform.rfid.tracker.interfaces.model;

public class ULDModel {

	private String uldNo;
	private String flightNumber;
	private String flightDate;
	private String carrier;
	private String flightKey;
	private Integer assignedTotalPcs;
	private String created_DateTime;
	private String lastUpdated_DateTime;
	private String createdUser;
	private String lastUpdated_User;
	private String assignedTotalWt;
	
	
	public String getUldNo() {
		return uldNo;
	}

	public void setUldNo(String uldNo) {
		this.uldNo = uldNo;
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

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getFlightKey() {
		return flightKey;
	}

	public void setFlightKey(String flightKey) {
		this.flightKey = flightKey;
	}

	public Integer getAssignedTotalPcs() {
		return assignedTotalPcs;
	}

	public void setAssignedTotalPcs(Integer assignedTotalPcs) {
		this.assignedTotalPcs = assignedTotalPcs;
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

	public String getAssignedTotalWt() {
		return assignedTotalWt;
	}

	public void setAssignedTotalWt(String assignedTotalWt) {
		this.assignedTotalWt = assignedTotalWt;
	}

}
