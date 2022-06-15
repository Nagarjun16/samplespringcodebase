package com.ngen.cosys.shipment.airmail.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.shipment.airmail.model.LyingListShipment;
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
public class LyingListContainer extends BaseBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer shipmentId;
	
	/**
	 * Shipment Location
	 */
	private String storeLocation;
	
	/**
	 * Warehouse Location
	 */
	private String warehouseLocation;
	
	/**
	 * Location Type
	 */
	private String locationType;
	
	/**
	 * Destination
	 */
	private String destinationResp;
	
	/**
	 * Pieces
	 */
	private int pieces;
	
	/**
	 * Weight
	 */
	private BigDecimal weights;
	
	/**
	 * Booking Flight Number
	 */
	private String bookingFlight;
	
	/**
	 * Booking Flight Date
	 */
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate flightDate;
	
	/**
	 * Intact
	 */
	private boolean intact;
	
	private boolean selectParent;
	
	/**
	 * List of Lying list detail
	 */
	private List<LyingListShipment> lyingListShipment;

}

