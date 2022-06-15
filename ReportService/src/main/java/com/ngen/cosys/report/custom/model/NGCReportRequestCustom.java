package com.ngen.cosys.report.custom.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@XmlRootElement
@Component
@ToString
@Getter
@Setter
@NoArgsConstructor
public class NGCReportRequestCustom {

	private static final long serialVersionUID = 1L;

	private String report;
	@NotNull(message = "g.mandatory")
	private LocalDate fromDate;
	@NotNull(message = "g.mandatory")
	private LocalDate toDate;
	private String carrier;
	private String flight;
	private LocalDate flightDate;

	@NotNull(message = "g.mandatory")
	private String reportCategory;
	@NotNull(message = "g.mandatory")
	private String reportName;
	// @NotNull(message = "mandatory")
	private String reportQuery;
	private String reportOutput;
	private String reportInfo;
	private String userId;
	private String domesticInternational;
	private String messageType;
	private String exportImport;
	private String declarationStatus;
	private String reportHeader_en_US;
	private String reportHeader_zh_CN;
	private String shc;
	private String suppliesCode;
	private String dateSta;
	private String terminal;
	private String uldStatus;
	private String uldType;
	private String agent;
	private String status;
	private String sbr1;
	private String sbr2;
	private String unid;
	private String messageTypeFSU;
	private String subMessageTypeFSU;
	private String irregularityType;
	private String report_tabs;
	private String tenantAirportCode;
	private String tenantCityCode;
	

}
