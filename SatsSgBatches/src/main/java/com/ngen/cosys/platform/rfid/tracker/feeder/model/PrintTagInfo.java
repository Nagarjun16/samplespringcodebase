package com.ngen.cosys.platform.rfid.tracker.feeder.model;

import java.util.List;

import org.springframework.stereotype.Component;

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
public class PrintTagInfo {

	private String tagType;
	private String awbNo;
	private String uldNo;
	private String flightKey;
	private String flightDate;
	private Integer totalPcs;
	private Integer totalTags;
	private Integer totalTagPcs;
	private String epcCode;
	private List<TagForPrintAWB> tagList;
	private List<AWB> awbList;

}
