package com.ngen.cosys.platform.rfid.tracker.feeder.model;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ApiModel
@Component
@ToString
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrintAWBTagInfo {

	private String awbNo;
	private String hawbNo;
	private long totalPcs;
	private long totalTags;
	private String printTagType = "awb";
	private List<TagForPrintAWB> tagList;

	@JsonIgnore
	private Integer shipmentId;
	@JsonIgnore
	private String jobTimeDate;

}
