/**
 * 
 * AWBRequest.java
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
 * This class is the used for AWB Printer Model
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class AWBRequest implements Serializable {

   private static final long serialVersionUID = 947186432030916658L;
   
   private String awbNumBarCode;
   private String awbNumTextCode;
   private String printQueue;
   
}
