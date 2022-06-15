package com.ngen.cosys.shipment.airmail.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
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
@NoArgsConstructor
public class LyingListShipment extends BaseBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer shipmentId;
	
	private Integer shpInventoryId;
	
	private Integer inventoryFlightId;
	
	private Integer shipmentHouseId;
	
	/**
	 * Dispatch Number
	 */
	private int dispatchNumber;
	
	/**
	 * Mail Bag Number
	 */
	private String mailBagNumber;
	
	/**
	 * Pieces Detail
	 */
	private int piecesDetail;
	
	/**
	 * Weight Detail
	 */
	private BigDecimal weightDetail;
	
	/**
	 * Mail Type
	 */
	private String mailType;
	
	/**
	 * Origin
	 */
	private String org;
	
	/**
	 * Destination
	 */
	private String dest;
	
	/**
	 * Next Destination
	 */
	private String nextDest;
	
	/**
	 * DN Complete
	 */
	private boolean dnComp;
	
	/**
	 * Booking Flight Detail
	 */
	private String bookingFlightDetail;
	
	/**
	 * Booking Flight Date detail
	 */
	private LocalDate bookedFlightDateDetail;
	
	/**
	 * Remarks
	 */
	private String remarks;
	
	private boolean selectChild;
	
	

}

