package com.ngen.cosys.shipment.information.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This Model for Booking Shipment Flight for ICMS
 * 
 * @author Coforge Technologies Pvt. Ltd.
 *
 */
@Component
@Getter
@Setter
@ToString
@NgenAudit(entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT,entityRefFieldName = "flightID", eventName = NgenAuditEventType.MULTIPLE_SHIPMENT_BOOKING, repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.AWB,entityRefFieldName = "shipmentId", eventName = NgenAuditEventType.SHIPMENT_BOOKING, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityFieldName = "Flight Key", entityType = NgenAuditEntityType.AWB,entityRefFieldName = "flightID", eventName = NgenAuditEventType.MULTIPLE_SHIPMENT_BOOKING, repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB,entityRefFieldName = "shipmentId", eventName = NgenAuditEventType.MULTIPLE_SHIPMENT_BOOKING_DIMENSIONS_AND_REMARKS, repository = NgenAuditEventRepository.AWB)
public class BookingShipmentDetails {
	
//	private String ubrNumber;
//	private FlightPair flightPair;
//	private List<String> partSuffixList;
	private String weightUnit;
	private String volumeUnit;
	private String shipmentPrefix;
	private String masterDocumentNumber;
	private String shipmentOrigin;
	private String shipmentDestination;
	private String agentCode;
	private String customerCode;
	private String bookingSource;
	private String currency;
	private BigInteger totalNumberOfPieces;
	private double totalWeight;
	private double totalVolume;
	private String commodityCode;
	private String shipmentDescription;
	private List<DimensionDetails> dimensionDetails;
	private String numberOfPieces;
	private List<BookingFlightDetails> bookingFlightDetails;
	private String carrierCode;
	private BigInteger pieces;
	private double weight;
	private FlightPair flightPair;
	private List<String> partSuffixList;
	private String ubrNumber;
	private String flightDate;
	private String flightNumber;
	private String segmentOrigin;
	private String segmentDestination;
	private String shipmentNumber;
	private String shipmentDate;


}
