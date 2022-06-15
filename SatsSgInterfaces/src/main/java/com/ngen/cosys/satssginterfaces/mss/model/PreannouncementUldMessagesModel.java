package com.ngen.cosys.satssginterfaces.mss.model;

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
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId", "terminal", "loggedInUser","userType", "sectorId" }) 
public class PreannouncementUldMessagesModel extends BaseBO {
   /**
    * system generated serial version id
    */
   private static final long serialVersionUID = 1L;
   /**
    *
    * This field contains Transaction Sequence Number
    */
   @JsonProperty(value = "TRA_REF_NUM")
   private String transactionSequenceNumber;
   /**
    *
    * This field contains Action
    */
   @JsonProperty(value = "ACTION")
   private String action;
   /**
    *
    * This field contains uldType
    */
   @JsonProperty(value = "ULD_TYP")
   private String uldType;
   /**
    *
    * This field contains uldNumber
    */
   @JsonProperty(value = "ULD_NUM")
   private String uldNumber;
   /**
    *
    * This field contains uldCarrier
    */
   @JsonProperty(value = "ULD_CAR")
   private String uldCarrier;
   /**
    *
    * This field contains Container Destination
    */
   @JsonProperty(value = "ULD_DES")
   private String containerDestination;
   /**
    *
    * This field contains In Carrier
    */
   @JsonProperty(value = "IN_FLT_CAR")
   private String inCarrier;
   /**
    *
    * This field contains In Flight
    */
   @JsonProperty(value = "IN_FLT_NUM")
   private String inFlightNumber;
   /**
    *
    * This field contains In Flight
    */
   @JsonProperty(value = "IN_FLT_DATE")
   private String inFlightDate;
   /**
    *
    * This field contains Indicative Outing Carrier For Container
    */
   @JsonProperty(value = "OUT_FLT_CAR")
   private String indicativeOutgoingCarrierForContainer;
   /**
    *
    * This field contains indicative outing flight number for container
    */
   @JsonProperty(value = "OUT_FLT_NUM")
   private String indicativeOutgoingFlightNumberForContainer;
   /**
    *
    * This field contains indicative outing flight date container
    */
   @JsonProperty(value = "OUT_FLT_DATE")
   private String indicativeOutgoingFlightDateForContainer;
   /**
    *
    * This field contains intact indicator for delivery
    */
   @JsonProperty(value = "DLV_BUP")
   private String intactIndicatorForlocalDelivery ="";
   /**
    *
    * This field contains transit intact indicator
    */
   @JsonProperty(value = "TRA_BUP")
   private String transitIntactIndicator ="Y";
   /**
    *
    * This field contains short transit indicator
    */
   @JsonProperty(value = "ST_IND")
   private String shortTransitIndicator = "";
   /**
    *
    * This field contains remarks for container
    */
   @JsonProperty(value = "ULD_RMKS")
   private String remarksForContainer = "";
}