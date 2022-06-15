package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "StringFormat", "locale", "messageList", "flagCRUD", "tenantId", "terminal", "loggedInUser" })
public class InboundBreakdownShipmentHouseAirmailInterfaceModel extends BaseBO {
   /**
    * System generated default serial version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger shipmentId;
   private BigInteger shipmentInventoryId;
   private BigInteger shipmentHouseId;

   private String type;
   private String number;

   private BigInteger pieces;

   private BigDecimal weight;

   // Mail Properties
   private String originOfficeExchange;
   private String mailCategory;
   private String mailType;
   private String mailSubType; 
   private BigInteger dispatchYear;
   private String dispatchNumber;
   private String receptacleNumber;
   private Boolean lastBag = Boolean.FALSE;
   private Boolean registered = Boolean.FALSE;
   private String destinationOfficeExchange;
   private String nextDestination;
   private String transferCarrier;
   private String shipmentMailType;


}