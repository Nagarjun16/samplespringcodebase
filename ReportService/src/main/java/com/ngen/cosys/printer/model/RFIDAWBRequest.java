/**
 * 
 * RFIDAWBRequest.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          29 AUG, 2018   NIIT      -
 */
package com.ngen.cosys.printer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is the used for RFID AWB Printer Model
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class RFIDAWBRequest {

	private String epcCode;
	private String hexaCollectiveUserMemory;
	private String tagNo;
	private String awbNo;
	private String displayAwbNo;
	private String qty;
	
}
