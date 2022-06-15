/**
 * 
 * \ * ShipmentFlightPartBookingDetails.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 15 December, 2017 NIIT -
 */
package com.ngen.cosys.icms.model.BookingInterface;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This model class represents ShipmentFlightPartBookingDetails Request from UI.
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
@NgenAudit(entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.SHIPMENT_BOOKING_CANCELLATION, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.SHIPMENT_BOOKING, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.SHIPMENTPART_BOOKING, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.FLIGHTPART_BOOKING, repository = NgenAuditEventRepository.AWB)
public class ShipmentFlightPartBookingDetails extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	private Long partBookingId;
	private Long bookingId;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.PART_SUFFIX)
	private String partSuffix;
	private boolean cancelFlag = false;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.PIECES)
	private Integer partPieces;
	private Double partWeight;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.WORKING_LIST_REMARKS)
	private String workingListRemarks;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.MANIFEST_REMARKS)
	private String manifestRemarks;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.INCOMING_FLIGHT)
	private String inComingFlight;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENT_REMARKS)
	private String additionalRemarks;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHT)
	private Double partWeightAudit;
	private String partIdentifier;
	private boolean mergeSelect = false;
	private String flagUpdate = "Y";
	private String unitCode;
	private Integer totalDimentionPieces;
	private Double volumetricWeight;
	private Integer densityGroupCode;
	private String volumeWeightUnitCode;
	private Double totalDimentionVolumetricWeight;
	private String measurementUnitCode;
	private boolean throughTransitFlag;
	private String outGoingFlight;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.VOLUME)

	private Double volume = 0.00;
	private String volumeUnitCode = "MC";
	@NgenCosysAppAnnotation
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHT_DETAILS)
	@Valid
	private List<ShipmentFlightBookingDetails> ShipmentFlightBookingList;

	private List<ShipmentPartBookingDimensionDetails> shipmentPartBookingDimensionList;
	private boolean suffixAvail;
	private String newPart;
	private int partIndex;
	private Double tempVolume;
	private List<Long> changedStatusCodes;

	private boolean skipPartValidation;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.PART_BOOKING_STATUS_CODE)
	private String partBookingStatusCode;
	private String partSource;
	private Boolean isFLightDeparted = Boolean.FALSE;
	private boolean pshTriggerFlag;
	private String partSourceSuffix;
	private String transferType;
	private boolean wasForceTT;
	private String tempVolumeCode;
	private Integer tempDensityGroupCode;
	private boolean volumeDisabledFlag;
	private boolean fromAddNewPart;
	private boolean partPiecesWtFlag = Boolean.FALSE;
	private Integer parentPieces;
	private Double parentWeight;
	private boolean fromCreatePart = Boolean.FALSE;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
	private String shipmentNumber;
	private String shipmentDate;
	private boolean select;
	private Integer inventoryPieces;
	private Double inventoryWeight;
	private String status;
	private Integer diffPcs;
	private Double diffWt;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTSHC)
	private String concatenatedShc;
	private Boolean isFlightManifested = Boolean.FALSE;
	private boolean partDeletionFlag = Boolean.FALSE;
	private boolean createPartInvocationFlag = Boolean.FALSE;
	private Long flightId;

	private String flightRoute;
	private boolean isWorkedOn;
}
