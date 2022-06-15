package com.ngen.cosys.platform.rfid.tracker.feeder.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Component
@ToString
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagInfo {
	private String eventDateTime;
	private String flowType;
	private String stageName;
	private String awbNo;
	private String uldNo;
	private String btNo;
	private String flightKey;
	private String flightDate;
	private String storageLocation;
	private String epcCode;
}
