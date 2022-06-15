package com.ngen.cosys.platform.rfid.tracker.feeder.model;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ScanFeed {
	public enum Stage {
		UNLOAD("UNLOAD"), 
		BAYPICKUP("BAY PICK UP"), 
		BREAKDOWN("BREAKDOWN"), 
		DELIVERY("DELIVERY"), 
		STORAGE("STORAGE"), 
		PRINT("PRINT"), 
		RECEIVED("RECEIVED"), 
		ACCEPTANCE("ACCEPTANCE"), 
		BUILDUP("BUILDUP"), 
		HANDOVER("HANDOVER"), 
		BAYDROPOFF("BAY DROP OFF"), 
		FLIGHTLOAD("FLIGHT LOAD"), 
		OFFLOAD("OFFLOAD"), 
		CANCEL("CANCEL");
		public String stage;

		Stage(String stage) {
			this.stage = stage;
		}
	}

	public enum FlowType {
		EXPORT("EXPORT"), 
		IMPORT("IMPORT");
		public String flowType;

		FlowType(String flowType) {
			this.flowType = flowType;
		}
	}

	private long msgSeqNo;
	private long tagId;
	private long tagNo;
	private Integer stationId;
	private String rfidTag;
	private String uldRfidTag;
	private String flowType;
	private String stageName;
	private String flagUldAwb;
	private String awbNumber;
	private long ShipmentId;
	private String uldBtNumber;
	private long containerTagid;
	private String location;
	private String flightKey;
	// @JsonFormat(timezone = "Asia/Singapore", pattern = "yyyy-MM-dd")
	private LocalDate flightOriginDate;
	private long tagPieces;
	private Integer totalPieces;
	private Integer totalTags;
	private Integer totalTagPieces;
	private String remarks;
	private String userId;
	// @JsonFormat(timezone = "Asia/Singapore", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date scanDate;
	// @JsonFormat(timezone = "Asia/Singapore", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdDate;

}
