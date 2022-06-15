package com.ngen.cosys.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.InterfaceLocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = { "deletedOn", "deletedBy", "deleteFlag", "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete", "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId",
      "terminal", "loggedInUser" })
public class ICSEmbargoRulesMessageModel extends BaseBO {

   /**
    * default serial version id
    */
   private static final long serialVersionUID = 1L;
   @JsonProperty(value = "TRA_REF_NUM")
   private String transactionSequenceNumber;
   @JsonProperty(value = "ACTION")
   private String action;
   @JsonProperty(value = "RULE_KEY")
   private String sharedKeyInformation;
   @JsonProperty(value = "AGNT_COD")
   private String agentCode;
   @JsonProperty(value = "IN_FLT_CAR")
   private String incomingCarrier;
   @JsonProperty(value = "OUT_FLT_CAR")
   private String outgoingCarrier;
   @JsonProperty(value = "ORI_IPMC")
   private String originIMPC;
   @JsonProperty(value = "ORI_COU")
   private String originCountryCode;
   @JsonProperty(value = "ORI_CTY")
   private String originCity;
   @JsonProperty(value = "DES_IMPC")
   private String destinationIMPC;
   @JsonProperty(value = "DES_COU")
   private String destinationCountry;
   @JsonProperty(value = "DES_CTY")
   private String destinationCity;
   @JsonProperty(value = "PRD_FROM")
   @JsonSerialize(using = InterfaceLocalDateSerializer.class)
   private LocalDate effctivePeriodFrom;
   @JsonProperty(value = "PRD_TO")
   @JsonSerialize(using = InterfaceLocalDateSerializer.class)
   private LocalDate effectivePeriodTo;
   private Boolean deleteFlag = Boolean.FALSE;
   private String deletedBy;
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate deletedOn;
}
