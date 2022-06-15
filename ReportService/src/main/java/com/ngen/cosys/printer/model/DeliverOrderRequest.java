/**
 * 
 * DeliverOrderRequest.java
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
 * This class is the used for Delivery Order Printer Model
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class DeliverOrderRequest implements Serializable {

   private static final long serialVersionUID = -7308139293285561081L;
   
   private String regnNo;
   private String doCode;
   private String doNo;
   private String qnty;
   private String cmod;
   private String accs;
   private String desc;
   private String cnee;
   private String agt;
   private String dlvy;
   private String chargeDue;
   private String ert;
   private String dlvyTo;
   private String dlvyBy;
   private String signature;
   private String timeComp;
   private String printQueue;
   
}
