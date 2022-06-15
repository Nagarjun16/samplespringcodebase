/**
 * 
 * NAWBRequest.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          10 JUL, 2018   NIIT      -
 */
package com.ngen.cosys.printer.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is the used for NAWBRequest Printer Model
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class NAWBRequest implements Serializable {

   private static final long serialVersionUID = 270409476694637911L;

   private String nawbNo;
   private String barcode;
   private String shipperAccNo;
   private String shipperName;
   private String shipperAdd;
   private String name;
   private String issuedBy;
   private String consigneeAccNo;
   private String consigneeName;
   private String consigneeAdd;
   private String carrierAgentName;
   private String carrierAgentCity;
   private String accountInfo;
   private String agentCode;
   private String accountNo;
   private String departApt; 
   private String refNo;
   private String tc; 
   private String fCarrier; 
   private String toSect;
   private String bySect;
   private String secToSect; 
   private String secBySect;
   private String currencies;
   private String chgsCode;
   private String wtPPD;
   private String wtColl;
   private String othPPD;
   private String othColl;
   private String carriage;
   private String customValue;
   private String destApt;
   private String reqFlight;
   private String reqDate;
   private String insurance; 
   private String handlingInfo;
   private String sci;
   private List<NAWBDetails> nawbDetails;
   private String natureOfGoods;
   private String totalNoPieces;
   private String totalGrossWeight;
   private String totalCost;
   private String prepaidWc;
   private String collectWc;
   private String prepaidVc;
   private String collectVc;
   private String prepaidTax;
   private String collectTax;
   private String prepaidOcA;
   private String collectOcA;
   private String prepaidOcC;
   private String collectOcC;
   private String totalPrepaid;
   private String totalCollect;
   private String ccCharges;
   private String ccRate;
   private String totalChargers;
   private String destCharges;
   private String shipperSign;
   private String executeDate;
   private String place;
   private List<NAWBDetails> otherChargers;
   private String printQueue;
   
   /**
    * NAWB Request Details
    */
   @Getter
   @Setter
   @NoArgsConstructor
   public static class NAWBDetails {
      
      private String noPieces;
      private String grossWeight;
      private String kg;
      private String rateClass;
      private String itemNo;
      private String chargableWeight; 
      private String rateCharges;
      private String total;
  	  private String otherCharges;
      
  }
   
}
