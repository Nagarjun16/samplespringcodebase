/**
 * 
 * HandOverVORequest.java
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
 * This class is the used for HandOverVO Printer Model
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class HandOverVORequest implements Serializable {

   private static final long serialVersionUID = 520640055134425584L;
   
   private String driverID;
   private String flightNo;
   private String date;
   private String std;
   private String etd;
   private String bay;
   private String satsID;
   private List<String> uldNoDate;
   private String printQueue;
   
}
