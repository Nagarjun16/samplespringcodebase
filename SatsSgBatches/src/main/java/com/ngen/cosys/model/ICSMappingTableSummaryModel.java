package com.ngen.cosys.model;

import java.math.BigInteger;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = { "deleteFlag", "deletedBy", "deletedOn", "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete", "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId",
      "terminal", "loggedInUser" })
public class ICSMappingTableSummaryModel extends BaseBO {
   /**
    * 
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
   @JsonProperty(value = "MAIL_DN")
   private String mailBagDispatchIdentifier;
   @JsonProperty(value = "TOT_PCS")
   private BigInteger indicativeTotalPieces;
   private Boolean deleteFlag = Boolean.FALSE;
   private String deletedBy;
   private LocalDate deletedOn;

}
