/**
 * 
 * TransferManifestRequest.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          10 JUL, 2018   NIIT      -
 */
package com.ngen.cosys.printer.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used for Transfer Manifest Print Model
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class TransferManifestRequest {

   private String transferringCarrier; 
   private String tmNo;
   private String airport;
   private String date;
   private String receivingCarrier;
   private String userId;
   private String receivetime;
   private String receiveDate;
   private String printQueue;
   private List<TransferManifestDetails> transferManifestDetails;
   
   @Getter
   @Setter
   @NoArgsConstructor
   public  class TransferManifestDetails {
      
      private String awbNo;
      private String awbDest;
      private String noPackage;
      private String weight;
      private String remarks;
      
  }
   
}
