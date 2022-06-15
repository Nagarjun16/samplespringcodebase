/**
 * 
 * Pouch.java
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
 * This class is used for Pouch Print
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class PouchRequest implements Serializable {

   private static final long serialVersionUID = 7693302791777935664L;
   
   private String flightNo;
   private String flightDate;
   private String offPoint;
   private String pouchId;
   private String pouchLbl;
   private String pouchDepartLoc;
   private String printQueue;
   
}
