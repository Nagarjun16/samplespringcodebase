package com.ngen.cosys.transhipment.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.transhipment.validator.group.TransferManifestByAWBMaintain;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.annotations.CheckValidFlightConstraint;
import com.ngen.cosys.validator.enums.FlightType;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@ApiModel
@Setter
@Getter
@NoArgsConstructor
@CheckValidFlightConstraint(number = "inboundFlightNumber", originDate = "inboundFlightDate", type = FlightType.Type.IMPORT, groups = TransferManifestByAWBMaintain.class)
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_TRM_AWB, repository = NgenAuditEventRepository.AWB, entityRefFieldName = "shipmentDate")
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CANCEL_TRM_AWB, repository = NgenAuditEventRepository.AWB, entityRefFieldName = "shipmentDate")
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.FINALIZE_TRM_AWB, repository = NgenAuditEventRepository.AWB, entityRefFieldName = "shipmentDate")
public class TranshipmentTransferManifestByAWBInfo extends BaseBO {
	
	/**
	* System generated serial version id 
	*/
	private static final long serialVersionUID = 1L;
	private BigInteger transTransferManifestByAwbId;

	private BigInteger transTransferManifestByAWBInfoId;

	@NgenAuditField(fieldName = "Flight Number")
	private String inboundFlightNumber;

	@NgenAuditField(fieldName = "Flight Date")
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate inboundFlightDate;

	private BigDecimal inboundFlightId;
	
	@NgenAuditField(fieldName = "Flight Handler")
	@NotNull(groups = { TransferManifestByAWBMaintain.class })
	private String inboundFlightHandler;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN)
	private String origin;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION)
	private String awbDestination;

	@NotNull(groups = { TransferManifestByAWBMaintain.class })
	private String destination;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.PIECES)
	@NotNull(groups = { TransferManifestByAWBMaintain.class })
	private int pieces;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHT)
	@NotNull(groups = { TransferManifestByAWBMaintain.class })
	private BigDecimal weight;

	@NotNull(groups = { TransferManifestByAWBMaintain.class })
	private String weightUnitCode;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.NATUREOFGOODS)
	private String natureOfGoodsDescription;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHP_REMARK)
	private String remarks;

	private String shcs;

	@NotEmpty(message = "g.required", groups = TransferManifestByAWBMaintain.class)
	@CheckShipmentNumberConstraint(groups = {
			TransferManifestByAWBMaintain.class }, mandatory = MandatoryType.Type.REQUIRED)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
	private String shipmentNumber;

	@JsonSerialize(using = LocalDateSerializer.class)
	@InjectShipmentDate(shipmentNumberField = "shipmentNumber")
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTDATE)
	private LocalDate shipmentDate;

	@Valid
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHCSB)
	private List<TranshipmentTransferManifestByAWBSHC> shcList;

	private boolean select;

	private BigInteger transactionSequenceNumber;
	
	private int irregularityPieces;
	private int inventoryPieces;
	private boolean exportDataFlag;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.RECEIVING_CARRIER)
	private String recievingCarrier;
	
	public String noPackage;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.TRM_NUMBER)
	private String trmNumber;

	// For Transferring data from shipment inventory to freight out table
	private BigInteger shipmentId;
	private BigInteger shipmentInventoryId;
	private BigInteger shipmentFreightOutId;
	private String transferCarrier;
	
	//Audit trail purpose field
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FINALIZED_STATUS)
	private String finalizedFlag;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CANCELLATION_REASON)
	private String cancellationReason;
	
	private BigDecimal inventoryWeight;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.AIRLINE_NUMBER)
	private String airlineNumber;
}