package com.ngen.cosys.printer.model;

import java.io.Serializable;

/**
 * 
 * UldTag.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          10 JUL, 2018   NIIT      -
 */
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used for ULD Tag Printer
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class ULDTagRequest implements Serializable {

   private static final long serialVersionUID = 5766477365193012778L;
   
   private String user;
   private String date;
   private String time;
   private String uldtagId;
   private String destination;
   private String netweight;
   private String tareweight;
   private String totalweight;
   private String loaded;
   private String flightNo;
   private String flightDate;
   private String content;
   private String remark;
   private String para1;
   private String para2;
   private String para3;
   private String para4;
   private String para5;
   private String para6;
   private String para7;
   private String para8;
   private String para9;
   private String para10;
   private String para11;
   private String para12;
   private String para13;
   private String para14;
   private String para15;
   private String para16;
   private String para17;
   private String para18;
   private String printQueue;
   
}
