package com.ngen.cosys.aed.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
		"flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "loggedInUser", "flagCRUD", "tenantId",
		"terminal" })
@JsonPropertyOrder(value = { "GHAMAWBINFO_ID", "RECORD_IND", "MAWB_NO", "TOTAL_ACTUAL_WT", "TOTAL_ACTUAL_WT_UOM",
		"PERCENT_TOTAL_GROSS_WT","EXEMPTION_CODE"})
public class GhaMawbInfoRequestDetails {

	@JacksonXmlProperty(localName = "GHAMAWBINFO_ID")
	private String ghaMawbInfoId;

	@JacksonXmlProperty(localName = "RECORD_IND")
	private String recordInd;

	@JacksonXmlProperty(localName = "MAWB_NO")
	private String mawbNo;

	@JacksonXmlProperty(localName = "TOTAL_ACTUAL_WT")
	private String totalActualWt;

	@JacksonXmlProperty(localName = "TOTAL_ACTUAL_WT_UOM")
	private String totalActualWtUmo;

	@JacksonXmlProperty(localName = "PERCENT_TOTAL_GROSS_WT")
	private String percentTotalGross;
	
	@JacksonXmlProperty(localName = "EXEMPTION_CODE")
	private String exemptionCode;

}
