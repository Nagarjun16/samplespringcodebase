package com.ngen.cosys.platform.rfid.tracker.interfaces.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FlightsModel {

	private String carrier;
	private String flightNumber;
	private String flightKey;
	private String flightOriginDate;
	private String origin;
	private String destination;
	private String flightType;
	private String station;
	private String STD;
	private String STA;
	private boolean jointFlightFlag;
	private String parkingBay;
	private String aircraftRegNo;
	private String cgoAcftType;
	private String createdUserCode;
	private String createdDateTime;
	private String lastUpdatedUserCode;
	private String lastUpdatedDateTime;
	private List<FlightLegModel> flightLegs;

	@JsonIgnore
	private String frequencyFrom;
	@JsonIgnore
	private String frequencyTo;
	@JsonIgnore
	private String jobName;
	@JsonIgnore
	private Integer addFrquency;

	@JsonIgnore
	private String flightKeyDate;

	@JsonIgnore
	private String fromDate;

	@JsonIgnore
	private String toDate;

	@JsonIgnore
	private String lastDataPush;

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

	public String getLastDataPush() {
		return lastDataPush;
	}

	public void setLastDataPush(String lastDataPush) {
		this.lastDataPush = lastDataPush;
	}

	public String getFlightKeyDate() {
		return flightKeyDate;
	}

	public void setFlightKeyDate(String flightKeyDate) {
		this.flightKeyDate = flightKeyDate;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getFlightKey() {
		return flightKey;
	}

	public void setFlightKey(String flightKey) {
		this.flightKey = flightKey;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getFlightType() {
		return flightType;
	}

	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getSTA() {
		return STA;
	}

	public void setSTA(String sTA) {
		STA = sTA;
	}

	public String getSTD() {
		return STD;
	}

	public void setSTD(String sTD) {
		STD = sTD;
	}

	public boolean isJointFlightFlag() {
		return jointFlightFlag;
	}

	public void setJointFlightFlag(boolean jointFlightFlag) {
		this.jointFlightFlag = jointFlightFlag;
	}

	public String getParkingBay() {
		return parkingBay;
	}

	public void setParkingBay(String parkingBay) {
		this.parkingBay = parkingBay;
	}

	public String getAircraftRegNo() {
		return aircraftRegNo;
	}

	public void setAircraftRegNo(String aircraftRegNo) {
		this.aircraftRegNo = aircraftRegNo;
	}

	public String getCgoAcftType() {
		return cgoAcftType;
	}

	public void setCgoAcftType(String cgoAcftType) {
		this.cgoAcftType = cgoAcftType;
	}

	public String getCreatedUserCode() {
		return createdUserCode;
	}

	public void setCreatedUserCode(String createdUserCode) {
		this.createdUserCode = createdUserCode;
	}

	public String getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(String createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getLastUpdatedUserCode() {
		return lastUpdatedUserCode;
	}

	public void setLastUpdatedUserCode(String lastUpdatedUserCode) {
		this.lastUpdatedUserCode = lastUpdatedUserCode;
	}

	public String getLastUpdatedDateTime() {
		return lastUpdatedDateTime;
	}

	public void setLastUpdatedDateTime(String lastUpdatedDateTime) {
		this.lastUpdatedDateTime = lastUpdatedDateTime;
	}

	public String getFlightOriginDate() {
		return flightOriginDate;
	}

	public void setFlightOriginDate(String flightOriginDate) {
		this.flightOriginDate = flightOriginDate;
	}

	 

	public List<FlightLegModel> getFlightLegs() {
		return flightLegs;
	}

	public void setFlightLegs(List<FlightLegModel> flightLegs) {
		this.flightLegs = flightLegs;
	}

	public String getFrequencyFrom() {
		return frequencyFrom;
	}

	public void setFrequencyFrom(String frequencyFrom) {
		this.frequencyFrom = frequencyFrom;
	}

	public String getFrequencyTo() {
		return frequencyTo;
	}

	public void setFrequencyTo(String frequencyTo) {
		this.frequencyTo = frequencyTo;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Integer getAddFrquency() {
		return addFrquency;
	}

	public void setAddFrquency(Integer addFrquency) {
		this.addFrquency = addFrquency;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((STA == null) ? 0 : STA.hashCode());
		result = prime * result + ((STD == null) ? 0 : STD.hashCode());
		result = prime * result + ((addFrquency == null) ? 0 : addFrquency.hashCode());
		result = prime * result + ((aircraftRegNo == null) ? 0 : aircraftRegNo.hashCode());
		result = prime * result + ((carrier == null) ? 0 : carrier.hashCode());
		result = prime * result + ((cgoAcftType == null) ? 0 : cgoAcftType.hashCode());
		result = prime * result + ((createdDateTime == null) ? 0 : createdDateTime.hashCode());
		result = prime * result + ((createdUserCode == null) ? 0 : createdUserCode.hashCode());
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((flightKey == null) ? 0 : flightKey.hashCode());
		result = prime * result + ((flightLegs == null) ? 0 : flightLegs.hashCode());
		result = prime * result + ((flightNumber == null) ? 0 : flightNumber.hashCode());
		result = prime * result + ((flightOriginDate == null) ? 0 : flightOriginDate.hashCode());
		result = prime * result + ((flightType == null) ? 0 : flightType.hashCode());
		result = prime * result + ((frequencyFrom == null) ? 0 : frequencyFrom.hashCode());
		result = prime * result + ((frequencyTo == null) ? 0 : frequencyTo.hashCode());
		result = prime * result + ((jobName == null) ? 0 : jobName.hashCode());
		result = prime * result + (jointFlightFlag ? 1231 : 1237);
		result = prime * result + ((lastUpdatedDateTime == null) ? 0 : lastUpdatedDateTime.hashCode());
		result = prime * result + ((lastUpdatedUserCode == null) ? 0 : lastUpdatedUserCode.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result + ((parkingBay == null) ? 0 : parkingBay.hashCode());
		result = prime * result + ((station == null) ? 0 : station.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlightsModel other = (FlightsModel) obj;
		if (STA == null) {
			if (other.STA != null)
				return false;
		} else if (!STA.equals(other.STA))
			return false;
		if (STD == null) {
			if (other.STD != null)
				return false;
		} else if (!STD.equals(other.STD))
			return false;
		if (addFrquency == null) {
			if (other.addFrquency != null)
				return false;
		} else if (!addFrquency.equals(other.addFrquency))
			return false;
		if (aircraftRegNo == null) {
			if (other.aircraftRegNo != null)
				return false;
		} else if (!aircraftRegNo.equals(other.aircraftRegNo))
			return false;
		if (carrier == null) {
			if (other.carrier != null)
				return false;
		} else if (!carrier.equals(other.carrier))
			return false;
		if (cgoAcftType == null) {
			if (other.cgoAcftType != null)
				return false;
		} else if (!cgoAcftType.equals(other.cgoAcftType))
			return false;
		if (createdDateTime == null) {
			if (other.createdDateTime != null)
				return false;
		} else if (!createdDateTime.equals(other.createdDateTime))
			return false;
		if (createdUserCode == null) {
			if (other.createdUserCode != null)
				return false;
		} else if (!createdUserCode.equals(other.createdUserCode))
			return false;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (flightKey == null) {
			if (other.flightKey != null)
				return false;
		} else if (!flightKey.equals(other.flightKey))
			return false;
		if (flightLegs == null) {
			if (other.flightLegs != null)
				return false;
		} else if (!flightLegs.equals(other.flightLegs))
			return false;
		if (flightNumber == null) {
			if (other.flightNumber != null)
				return false;
		} else if (!flightNumber.equals(other.flightNumber))
			return false;
		if (flightOriginDate == null) {
			if (other.flightOriginDate != null)
				return false;
		} else if (!flightOriginDate.equals(other.flightOriginDate))
			return false;
		if (flightType == null) {
			if (other.flightType != null)
				return false;
		} else if (!flightType.equals(other.flightType))
			return false;
		if (frequencyFrom == null) {
			if (other.frequencyFrom != null)
				return false;
		} else if (!frequencyFrom.equals(other.frequencyFrom))
			return false;
		if (frequencyTo == null) {
			if (other.frequencyTo != null)
				return false;
		} else if (!frequencyTo.equals(other.frequencyTo))
			return false;
		if (jobName == null) {
			if (other.jobName != null)
				return false;
		} else if (!jobName.equals(other.jobName))
			return false;
		if (jointFlightFlag != other.jointFlightFlag)
			return false;
		if (lastUpdatedDateTime == null) {
			if (other.lastUpdatedDateTime != null)
				return false;
		} else if (!lastUpdatedDateTime.equals(other.lastUpdatedDateTime))
			return false;
		if (lastUpdatedUserCode == null) {
			if (other.lastUpdatedUserCode != null)
				return false;
		} else if (!lastUpdatedUserCode.equals(other.lastUpdatedUserCode))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		if (parkingBay == null) {
			if (other.parkingBay != null)
				return false;
		} else if (!parkingBay.equals(other.parkingBay))
			return false;
		if (station == null) {
			if (other.station != null)
				return false;
		} else if (!station.equals(other.station))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FlightBO [carrier=" + carrier + ", flightNumber=" + flightNumber + ", flightKey=" + flightKey
				+ ", origin=" + origin + ", destination=" + destination + ", flightType=" + flightType + ", station="
				+ station + ", STA=" + STA + ", STD=" + STD + ", jointFlightFlag=" + jointFlightFlag + ", parkingBay="
				+ parkingBay + ", aircraftRegNo=" + aircraftRegNo + ", cgoAcftType=" + cgoAcftType
				+ ", createdUserCode=" + createdUserCode + ", createdDateTime=" + createdDateTime
				+ ", lastUpdatedUserCode=" + lastUpdatedUserCode + ", lastUpdatedDateTime=" + lastUpdatedDateTime
				+ ", flightOriginDate=" + flightOriginDate + ", flightLegs=" + flightLegs + ", frequencyFrom="
				+ frequencyFrom + ", frequencyTo=" + frequencyTo + ", jobName=" + jobName + ", addFrquency="
				+ addFrquency + "]";
	}

}
