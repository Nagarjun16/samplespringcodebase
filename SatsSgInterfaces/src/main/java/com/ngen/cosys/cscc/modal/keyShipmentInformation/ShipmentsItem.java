package com.ngen.cosys.cscc.modal.keyShipmentInformation;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Generated;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class ShipmentsItem implements Serializable {
	@JsonProperty("AWBNo")
	private String AWBNo;

	@JsonProperty("importULDS")
	private List<String> importULDS;

	@JsonProperty("SHC")
	private String SHC;

	@JsonProperty("NOG")
	private String NOG;

	@JsonProperty("Origin")
	private String origin;

	@JsonProperty("Destination")
	private String destination;

	@JsonProperty("pcs")
	private int pcs;

	@JsonProperty("weight")
	private Object weight;

	@JsonProperty("bookingChanges")
	private boolean bookingChanges;

	@JsonProperty("isTransshipment")
	private boolean isTransshipment;

	@JsonProperty("flightNoIn")
	private List<String> flightNoIn;

	@JsonProperty("flightNoOut")
	private List<String> flightNoOut;

	@JsonProperty("acceptanceTime")
	private String acceptanceTime;

	@JsonProperty("buildUpTime")
	private String buildUpTime;

	@JsonProperty("AWBReceivedTime")
	private String aWBReceivedTime;

	@JsonProperty("AWBScannedTime")
	private String aWBScannedTime;

	@JsonProperty("loadedInfo")
	private List<String> loadedInfo;

	@JsonProperty("locationInfo")
	private List<String> locationInfo;

	@JsonProperty("screenFlag")
	private boolean screenFlag;

	@JsonProperty("screenedBy")
	private String screenedBy;

	@JsonProperty("screenedAt")
	private String screenedAt;

	@JsonProperty("offloadFinalizationTiming")
	private String offloadFinalizationTiming;

	@JsonProperty("rampReceivedTiming")
	private String rampReceivedTiming;

	@JsonProperty("XAF1")
	private String XAF1;

	@JsonProperty("XAF2")
	private String XAF2;

	@JsonProperty("XAF3")
	private String XAF3;

}