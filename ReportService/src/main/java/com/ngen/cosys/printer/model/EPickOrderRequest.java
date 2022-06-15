/**
 * 
 * EPickOrderRequest.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          10 JUL, 2018   NIIT      -
 */
package com.ngen.cosys.printer.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is the used for EPick Order Printer Model
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class EPickOrderRequest implements Serializable {

   private static final long serialVersionUID = -5498325681893862608L;
   
   private String pickOrderCode;
   private String poPrint;
   private String poNo; 
   private String uldDetails;
   private String truckDock; 
   private String consigneeDetails;
   private String apptAgent;
   private String personCollect;
   private String icNo;
   private String collectDate;
   private String awbNo;
   private String weight;
   private String nog;
   private String shc; 
   private String flightDate;
   private String pcs;
   private String agentExemptPermit;
   private String lar;
   private String loc;
   private String remarks;
   private String printQueue;
   
}
