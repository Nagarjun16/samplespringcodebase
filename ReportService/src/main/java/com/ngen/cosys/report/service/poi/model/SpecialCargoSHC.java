package com.ngen.cosys.report.service.poi.model;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@ApiModel
@XmlRootElement
@Component
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NgenCosysAppAnnotation
public class SpecialCargoSHC extends BaseBO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9202438459451975237L;
	
	//Shipment Master SHC
	private BigInteger shipmentMasterSHCId;
	private BigInteger shipmentMasterSHCShipmentId;
	private String shipmentMasterSpecialHandlingCode;
	private String shipmentMasterSHCGroupCollectionCode;
	private String shipmentMasterSpecialHandlingGroup;
	private BigInteger shipmentMasterSHCPriority;
	private BigInteger shipmentMasterSHCGroupPriority; 
	
	//Booking SHC
	private BigInteger flightBookingDetailSHCBookingId;
	private BigInteger flightBookingDetailSHCFlightBookingId;
	private BigInteger flightBookingSHCId;
	private String flightBookingSpecialHandlingCode;
	private String flightBookingSHCGroupCollectionCode;
	private String flightBookingSpecialHandlingGroup;
	private BigInteger flightBookingSHCPriority;
	private BigInteger flightBookingSHCGroupPriority; 
	
	//Part SHC
	private BigInteger PartBookingDetailSHCBookingId;
	private BigInteger PartBookingDetailSHCFlightBookingId;
	private BigInteger partBookingDetailSHCPartBookingID;
	private BigInteger partBookingSHCId;         
	private String partSpecialHandlingCode;
	private String partSHCGroupCollectionCode;
	private String partSpecialHandlingGroup;
	private BigInteger partSHCPriority;
	private BigInteger partSHCGroupPriority; 
	
	//Inventory SHC
	private BigInteger inventoryShcBookingId;
	private BigInteger inventoryShcFlightBookingId;
	private BigInteger shipmentInventorySHCId;
	private BigInteger shipmentInventoryId;
	private String inventorySpecialHandlingCode;
	private String inventorySHCGroupCollectionCode;
	private String inventorySpecialHandlingGroup;
	private BigInteger inventorySHCPriority;
	private BigInteger inventorySHCGroupPriority; 
	
	//To insert requested shc 
	private String code;
    private BigInteger specialCargoHandoverSHCId;
    private BigInteger specialCargoHandoverDtlId;
    
}
