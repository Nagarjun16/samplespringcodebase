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
public class ICSCloseTransitMessagesModel extends BaseBO {
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
   @JsonProperty(value = "ORI_IMPC")
   private String originatorPostalAuthorityIMPCCode;
   @JsonProperty(value = "DES_IMPC")
   private String destinationIMPC;
   @JsonProperty(value = "IN_FLT_CAR")
   private String incomingCarrier;
   @JsonProperty(value = "PRD_FRM")
   @JsonSerialize(using = InterfaceLocalDateSerializer.class)
   private LocalDate effectivePeriodFrom;
   @JsonProperty(value = "PRD_TO")
   @JsonSerialize(using = InterfaceLocalDateSerializer.class)
   private LocalDate effectivePeriodTo;
   private Boolean deleteFlag = Boolean.FALSE;
   private String deletedBy;
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate deletedOn;
}