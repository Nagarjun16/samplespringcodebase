package com.ngen.cosys.platform.rfid.tracker.feeder.model;

import java.util.List;

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
public class PrintULDTagInfo {
	private String uldNo;
	private String flightNo;
	private String flightDate;
	private long tagNo;
	private String epcCode;
	private String printTagType = "uld";
	private List<AWB> awbList;
}
