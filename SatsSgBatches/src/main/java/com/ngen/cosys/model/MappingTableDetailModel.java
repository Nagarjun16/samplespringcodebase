package com.ngen.cosys.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.InterfaceLocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = { "deletedOn","deletedBy","deleteFlag","createdBy", "createdOn", "modifiedBy", "modifiedOn",  "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId", "terminal", "loggedInUser", "userType", "sectorId" })
public class MappingTableDetailModel extends BaseBO {
   /**
    * Default serial version id
    */
   private static final long serialVersionUID = 1L;
   /**
   *
   * This field contains Transaction Sequence Number
   */
   @JsonProperty(value = "TRA_REF_NUM") 
   private String transactionSequenceNumber;
   @JsonProperty(value = "ACTION")
   private String action;
   @JsonProperty(value = "MAIL_BAG_ID")
   private String mailBagIdentifier;
   @JsonProperty(value = "OUT_FLT_CAR")
   private String indicativeOutgoingCarrier;
   @JsonProperty(value = "OUT_FLT_NUM")
   private String indicativeOutgoingFlightNumber;
   @JsonProperty(value = "OUT_FLT_DATE")
   @JsonSerialize(using = InterfaceLocalDateSerializer.class)
   private LocalDate indicativeOutgoingFlightDate;
   private Boolean deleteFlag = Boolean.FALSE;
   private String deletedBy;
   private LocalDate deletedOn;
}
