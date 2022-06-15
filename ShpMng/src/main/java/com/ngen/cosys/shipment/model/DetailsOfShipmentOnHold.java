/**
 * 
 * DetailsOfShipmentOnHold.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version   Date   Author   Reason
 * 1.0   3 Jan, 2018   NIIT   -
 */
package com.ngen.cosys.shipment.model;

import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.stereotype.Component;
import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This model class DetailsOfShipmentOnHold entity to able user to remove
 * shipment on hold or get to know other details regarding shipment on hold 
 * e.g. warehouse location, freight In, freight in date, SHC etc.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */

@ApiModel
@XmlRootElement
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DetailsOfShipmentOnHold {
	/**
	 * location from where shipment will be done
	 */
	private String shipmentLocation;
	
	/**
	 * number of pieces for the shipment
	 */
	private int pieces;
	
	/**
	 * ware house location
	 */
	private String wareHouseLocation;
	
	/**
	 * flight number
	 */
	private String freightIn;
	
	/**
	 * date on which flight has been scheduled
	 */
	private String freightInDate;
	
	/**
	 * shipment handling code
	 */
	private String shipmentHandlingCode;
	
	/**
	 * user can lock or unlock using hold option
	 */
	private boolean hold;
	
	/**
	 * name of the user
	 */
	private String user;
	
	/**
	 * date and time for shipment
	 */
	private String dateTime;
	
	/**
	 * reason for shipment on hold
	 */
	private String reasonForHold;
	
	/**
	 * remarks
	 */
	private String remarks;
	
	

}
