/**
 * 
 * ShipmentFlightBookingDetails.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          13 November, 2017    NIIT      -
 */
package com.ngen.cosys.icms.model.BookingInterface;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;


import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.processing.engine.rule.fact.FactFlight;
import com.ngen.cosys.processing.engine.rule.fact.FactShipmentSHC;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This model class represents ShipmentFlightBookingDetails Request from UI.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@Component
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Validated
//@NgenAudit(entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.SHIPMENT_BOOKING, repository = NgenAuditEventRepository.AWB)
//@NgenAudit(entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.SHIPMENT_BOOKING_CANCELLATION, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.SHIPMENTPART_BOOKING, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.FLIGHTPART_BOOKING, repository = NgenAuditEventRepository.AWB)
public class ShipmentFlightBookingDetails extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	private Long flightBookingId;
	private Long partBookingId;
	private Long bookingId;
	private int flightId;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.FLIGHTKEY)
	private String flightKey;
	private String oldFlightKey;
	private Long expWorkingListId;
	private boolean partShipmentFlag;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.FLIGHT_BOARD_POINT)
	private String flightBoardPoint;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.FLIGHT_OFF_POINT)
	private String flightOffPoint;
	private LocalDateTime departureTime;
	private LocalDateTime arrivalTime;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.DATESTD)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime dateSTD;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@NgenAuditField(fieldName=NgenAuditFieldNameType.DATESTA)
	private LocalDateTime dateSTA;
	private Integer bookingPieces;
	private Integer oldBookingPieces;
	private Integer newBookingPieces;
	private Double bookingWeight;

	private Double oldBookingWeight;
	private Double newBookingWeight;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.BOOKINGSTATUSCODE)
	private String bookingStatusCode;
	private int bookingCancellationFlag = 0;

	private transient LocalDate bookingCancellationDate;
	@JsonSerialize(using = LocalDateSerializer.class)
	@NgenAuditField(fieldName=NgenAuditFieldNameType.FLIGHT_ORIGIN_DATE)
	private LocalDate flightOriginDate;
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate oldFlightOriginDate;
	private String bookingCancellationUserCode;

	private BigInteger bookingCancellationReasonId;
	private String workingListRemarks;
	private String manifestRemarks;
	private String additionalRemarks;
	private String flagUpdate = "Y";

	private boolean mergeSelect = false;
	private BigInteger flightSegmentId;
	private BigInteger segmentId;
	private Integer outwardBookingFlag = 0;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.THROUGH_TRANSIT_INDICATOR)
	private boolean throughTransitFlag;

	private Long oldFlightId;
	private String oldPart;
	private String newPart;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.PIECES)
	private Integer partPieces;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.WEIGHT)
	private Double partWeight;
	private Long shipmentId;

	private Double volumetricWeight;
	private Integer densityGroupCode;
	private Double volume;
	private String volumeUnitCode;
	private String volumeWeightUnitCode;

	private Double totalDimentionVolumetricWeight;
	private String measurementUnitCode;

	private boolean wasForceTT;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.TRANSFER_TYPE)
	private String transferType;

	private String bookingCancellationReason;
	private boolean flightInMultiPart;
	private String source;
	private Boolean isFLightDeparted = Boolean.FALSE;
	private String partSuffix;
	private Long flightPartBookingId;
	private String partBookingStatusCode;
	private List<ShipmentBookingRemark> shipmentRemarks;
	private List<ShipmentPartBookingDimensionDetails> dimensionList;
	private boolean fromDeleteButton;
	private boolean flightCancelFlag;
	private BigInteger loadedPieces;
	private BigDecimal loadedWeight;
	private  List<FactShipmentSHC> factShc;
	private FactFlight factFlightDetails; 
	private String origin;
	private String destination;
	private String carrierCode;
	private String flightNumber;
	private Boolean isFlightManifested=Boolean.FALSE;
	private String commaSepratedShcs;
	private int index;
	private String remarks;
	private boolean isWorkedOn = Boolean.FALSE;
	private Boolean handlinginSystem =Boolean.FALSE;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.SHIPMENTNUMBER)
	private String shipmentNumber;
	// comparator for comparing segment STD

	public static Comparator<ShipmentFlightBookingDetails> stdComparator = new Comparator<ShipmentFlightBookingDetails>() {
		@Override
		public int compare(ShipmentFlightBookingDetails flight1, ShipmentFlightBookingDetails flight2) {
			if (flight1.getDateSTD().compareTo(flight2.getDateSTA()) > 0) {
				return 1;
			}else if(flight1.getDateSTD().compareTo(flight2.getDateSTA()) < 0) {
				return -1;
			}
			else {
				return 0;
			}
		}
	};
}
