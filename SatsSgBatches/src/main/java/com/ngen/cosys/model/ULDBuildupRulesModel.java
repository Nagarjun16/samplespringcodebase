package com.ngen.cosys.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

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
@JsonIgnoreProperties(value = { "deleteFlag","deletedBy","deletedOn","createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId", "terminal",
      "loggedInUser", "uldCarrierCode", "userType", "sectorId" })
public class ULDBuildupRulesModel extends BaseBO {
   /**
    * default serial version id
    */
   private static final long serialVersionUID = 1L;
   @JsonProperty(value = "TRA_REF_NUM")
   private String transactionSequenceNumber;
   @JsonProperty(value = "ACTION")
   private String action;
   @JsonProperty(value = "RULE_KEY")
   private String sharedKey;
   @JsonProperty(value = "OUT_FLT_CAR")
   private String outgoingCarrier;
   @JsonProperty(value = "ULD_TYP")
   private String uldType;
   @JsonProperty(value = "MAX_BAG_CNT")
   private BigInteger maxNoOfMailBag;
   @JsonProperty(value = "MAX_BAG_WGT")
   private BigInteger maxWeightOfMailBag;
   @JsonProperty(value = "AIR_TYP_LST")
   private List<AircraftTypesUldModel> aircraftTypeContainers;
   @JsonProperty(value = "PRD_FRM")
   @JsonSerialize(using = InterfaceLocalDateSerializer.class)
   // TODO Need Business Confirmation, also DB Columns are missing
   private LocalDate effectivePeriodFrom = LocalDate.of(1990, 9, 01);
   @JsonProperty(value = "PRD_TO")
   @JsonSerialize(using = InterfaceLocalDateSerializer.class)
   // TODO Need Business Confirmation, also DB Columns are missing
   private LocalDate effectivePeriodTo = LocalDate.of(2999, 12, 31);
   private Boolean deleteFlag = Boolean.FALSE;
   private String deletedBy;
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate deletedOn;
}