package com.ngen.cosys.platform.rfid.tracker.interfaces.model;

public class FlightLegModel {

	private String carrier;
	private String flightNumber;
	private String flightKey;
	private String flightBoardPoint;
	private String flightOffPoint;
	private Integer flightSegmentOrder;
	private String STD;
	private String STA;
	private String ETD;
	private String ETA;
	private String ATD;
	private String ATA;
	private boolean domesticFlightFlag;
	private String aircraftRegCode;
	private String aircraftType;
	private String parkingBay;
	private Float weight;
	private String weightUnit;
	private String createdUser_Code;
	private String created_DateTime;
	private String lastUpdatedUser_Code;
	private String lastUpdated_DateTime;

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

	public String getFlightBoardPoint() {
		return flightBoardPoint;
	}

	public void setFlightBoardPoint(String flightBoardPoint) {
		this.flightBoardPoint = flightBoardPoint;
	}

	public String getFlightOffPoint() {
		return flightOffPoint;
	}

	public void setFlightOffPoint(String flightOffPoint) {
		this.flightOffPoint = flightOffPoint;
	}

	public Integer getFlightSegmentOrder() {
		return flightSegmentOrder;
	}

	public void setFlightSegmentOrder(Integer flightSegmentOrder) {
		this.flightSegmentOrder = flightSegmentOrder;
	}

	public String getSTD() {
		return STD;
	}

	public void setSTD(String sTD) {
		STD = sTD;
	}

	public String getSTA() {
		return STA;
	}

	public void setSTA(String sTA) {
		STA = sTA;
	}

	public String getETD() {
		return ETD;
	}

	public void setETD(String eTD) {
		ETD = eTD;
	}

	public String getETA() {
		return ETA;
	}

	public void setETA(String eTA) {
		ETA = eTA;
	}

	public String getATD() {
		return ATD;
	}

	public void setATD(String aTD) {
		ATD = aTD;
	}

	public String getATA() {
		return ATA;
	}

	public void setATA(String aTA) {
		ATA = aTA;
	}

	public boolean isDomesticFlightFlag() {
		return domesticFlightFlag;
	}

	public void setDomesticFlightFlag(boolean domesticFlightFlag) {
		this.domesticFlightFlag = domesticFlightFlag;
	}

	public String getAircraftRegCode() {
		return aircraftRegCode;
	}

	public void setAircraftRegCode(String aircraftRegCode) {
		this.aircraftRegCode = aircraftRegCode;
	}

	public String getAircraftType() {
		return aircraftType;
	}

	public void setAircraftType(String aircraftType) {
		this.aircraftType = aircraftType;
	}

	public String getParkingBay() {
		return parkingBay;
	}

	public void setParkingBay(String parkingBay) {
		this.parkingBay = parkingBay;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public String getCreatedUser_Code() {
		return createdUser_Code;
	}

	public void setCreatedUser_Code(String createdUser_Code) {
		this.createdUser_Code = createdUser_Code;
	}

	public String getCreated_DateTime() {
		return created_DateTime;
	}

	public void setCreated_DateTime(String created_DateTime) {
		this.created_DateTime = created_DateTime;
	}

	public String getLastUpdatedUser_Code() {
		return lastUpdatedUser_Code;
	}

	public void setLastUpdatedUser_Code(String lastUpdatedUser_Code) {
		this.lastUpdatedUser_Code = lastUpdatedUser_Code;
	}

	public String getLastUpdated_DateTime() {
		return lastUpdated_DateTime;
	}

	public void setLastUpdated_DateTime(String lastUpdated_DateTime) {
		this.lastUpdated_DateTime = lastUpdated_DateTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ATA == null) ? 0 : ATA.hashCode());
		result = prime * result + ((ATD == null) ? 0 : ATD.hashCode());
		result = prime * result + ((ETA == null) ? 0 : ETA.hashCode());
		result = prime * result + ((ETD == null) ? 0 : ETD.hashCode());
		result = prime * result + ((STA == null) ? 0 : STA.hashCode());
		result = prime * result + ((STD == null) ? 0 : STD.hashCode());
		result = prime * result + ((aircraftRegCode == null) ? 0 : aircraftRegCode.hashCode());
		result = prime * result + ((aircraftType == null) ? 0 : aircraftType.hashCode());
		result = prime * result + ((carrier == null) ? 0 : carrier.hashCode());
		result = prime * result + ((createdUser_Code == null) ? 0 : createdUser_Code.hashCode());
		result = prime * result + ((created_DateTime == null) ? 0 : created_DateTime.hashCode());
		result = prime * result + (domesticFlightFlag ? 1231 : 1237);
		result = prime * result + ((flightBoardPoint == null) ? 0 : flightBoardPoint.hashCode());
		result = prime * result + ((flightKey == null) ? 0 : flightKey.hashCode());
		result = prime * result + ((flightNumber == null) ? 0 : flightNumber.hashCode());
		result = prime * result + ((flightOffPoint == null) ? 0 : flightOffPoint.hashCode());
		result = prime * result + ((flightSegmentOrder == null) ? 0 : flightSegmentOrder.hashCode());
		result = prime * result + ((lastUpdatedUser_Code == null) ? 0 : lastUpdatedUser_Code.hashCode());
		result = prime * result + ((lastUpdated_DateTime == null) ? 0 : lastUpdated_DateTime.hashCode());
		result = prime * result + ((parkingBay == null) ? 0 : parkingBay.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		result = prime * result + ((weightUnit == null) ? 0 : weightUnit.hashCode());
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
		FlightLegModel other = (FlightLegModel) obj;
		if (ATA == null) {
			if (other.ATA != null)
				return false;
		} else if (!ATA.equals(other.ATA))
			return false;
		if (ATD == null) {
			if (other.ATD != null)
				return false;
		} else if (!ATD.equals(other.ATD))
			return false;
		if (ETA == null) {
			if (other.ETA != null)
				return false;
		} else if (!ETA.equals(other.ETA))
			return false;
		if (ETD == null) {
			if (other.ETD != null)
				return false;
		} else if (!ETD.equals(other.ETD))
			return false;
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
		if (aircraftRegCode == null) {
			if (other.aircraftRegCode != null)
				return false;
		} else if (!aircraftRegCode.equals(other.aircraftRegCode))
			return false;
		if (aircraftType == null) {
			if (other.aircraftType != null)
				return false;
		} else if (!aircraftType.equals(other.aircraftType))
			return false;
		if (carrier == null) {
			if (other.carrier != null)
				return false;
		} else if (!carrier.equals(other.carrier))
			return false;
		if (createdUser_Code == null) {
			if (other.createdUser_Code != null)
				return false;
		} else if (!createdUser_Code.equals(other.createdUser_Code))
			return false;
		if (created_DateTime == null) {
			if (other.created_DateTime != null)
				return false;
		} else if (!created_DateTime.equals(other.created_DateTime))
			return false;
		if (domesticFlightFlag != other.domesticFlightFlag)
			return false;
		if (flightBoardPoint == null) {
			if (other.flightBoardPoint != null)
				return false;
		} else if (!flightBoardPoint.equals(other.flightBoardPoint))
			return false;
		if (flightKey == null) {
			if (other.flightKey != null)
				return false;
		} else if (!flightKey.equals(other.flightKey))
			return false;
		if (flightNumber == null) {
			if (other.flightNumber != null)
				return false;
		} else if (!flightNumber.equals(other.flightNumber))
			return false;
		if (flightOffPoint == null) {
			if (other.flightOffPoint != null)
				return false;
		} else if (!flightOffPoint.equals(other.flightOffPoint))
			return false;
		if (flightSegmentOrder == null) {
			if (other.flightSegmentOrder != null)
				return false;
		} else if (!flightSegmentOrder.equals(other.flightSegmentOrder))
			return false;
		if (lastUpdatedUser_Code == null) {
			if (other.lastUpdatedUser_Code != null)
				return false;
		} else if (!lastUpdatedUser_Code.equals(other.lastUpdatedUser_Code))
			return false;
		if (lastUpdated_DateTime == null) {
			if (other.lastUpdated_DateTime != null)
				return false;
		} else if (!lastUpdated_DateTime.equals(other.lastUpdated_DateTime))
			return false;
		if (parkingBay == null) {
			if (other.parkingBay != null)
				return false;
		} else if (!parkingBay.equals(other.parkingBay))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		if (weightUnit == null) {
			if (other.weightUnit != null)
				return false;
		} else if (!weightUnit.equals(other.weightUnit))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FlightLegBO [carrier=" + carrier + ", flightNumber=" + flightNumber + ", flightKey=" + flightKey
				+ ", flightBoardPoint=" + flightBoardPoint + ", flightOffPoint=" + flightOffPoint
				+ ", flightSegmentOrder=" + flightSegmentOrder + ", STD=" + STD + ", STA=" + STA + ", ETD=" + ETD
				+ ", ETA=" + ETA + ", ATD=" + ATD + ", ATA=" + ATA + ", domesticFlightFlag=" + domesticFlightFlag
				+ ", aircraftRegCode=" + aircraftRegCode + ", aircraftType=" + aircraftType + ", parkingBay="
				+ parkingBay + ", weight=" + weight + ", weightUnit=" + weightUnit + ", createdUser_Code="
				+ createdUser_Code + ", created_DateTime=" + created_DateTime + ", lastUpdatedUser_Code="
				+ lastUpdatedUser_Code + ", lastUpdated_DateTime=" + lastUpdated_DateTime + "]";
	}

}
