package com.ngen.cosys.aed.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@ApiModel
@NoArgsConstructor
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
		"flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "loggedInUser", "flagCRUD", "tenantId",
		"terminal","shipmentDate","userType","sectorId","shipmentDate","versionDateTime" })
@JsonPropertyOrder(value = { "SCSUMOFWTGHA_ID", "RECORD_IND", "MAWB_NO", "SUM_TOTAL_GROSS_WT", "SUM_TOTAL_GROSS_WT_UOM",
		"SC_PERCENT_TOLERANCE" })

public class ScSumOfWtGhaRequestDetails extends BaseBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDate shipmentDate;

	@JacksonXmlProperty(localName = "SCSUMOFWTGHA_ID")
	private String scSumOfWtghaId;

	@JacksonXmlProperty(localName = "RECORD_IND")
	private String recordInd;

	@JacksonXmlProperty(localName = "MAWB_NO")
	private String mawbNo;

	@JacksonXmlProperty(localName = "SUM_TOTAL_GROSS_WT")
	private BigDecimal sumTotalGrossWt;

	@JacksonXmlProperty(localName = "SUM_TOTAL_GROSS_WT_UOM")
	private String sumTotalGrossWtUom;

	@JacksonXmlProperty(localName = "SC_PERCENT_TOLERANCE")
	private BigDecimal scPecentTollerence;

}
