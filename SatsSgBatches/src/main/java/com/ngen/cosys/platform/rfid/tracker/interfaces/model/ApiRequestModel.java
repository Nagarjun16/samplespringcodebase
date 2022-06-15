package com.ngen.cosys.platform.rfid.tracker.interfaces.model;

import java.util.List;

public class ApiRequestModel {
	private String pushDataType;
	private List<FlightsModel> flightList;
	private List<AwbModel> awbList;
	private String fromDate;
	private String toDate;
	private String isLastRecord = "N";
	private String station;
	private String flightType;
	public String getPushDataType() {
		return pushDataType;
	}
	public void setPushDataType(String pushDataType) {
		this.pushDataType = pushDataType;
	}
	public List<FlightsModel> getFlightList() {
		return flightList;
	}
	public void setFlightList(List<FlightsModel> flightList) {
		this.flightList = flightList;
	}
	public List<AwbModel> getAwbList() {
		return awbList;
	}
	public void setAwbList(List<AwbModel> awbList) {
		this.awbList = awbList;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getIsLastRecord() {
		return isLastRecord;
	}
	public void setIsLastRecord(String isLastRecord) {
		this.isLastRecord = isLastRecord;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getFlightType() {
		return flightType;
	}
	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}

	 
}
