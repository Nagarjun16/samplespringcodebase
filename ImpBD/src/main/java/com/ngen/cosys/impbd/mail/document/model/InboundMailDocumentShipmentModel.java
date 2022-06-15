package com.ngen.cosys.impbd.mail.document.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.stereotype.Component;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEntityValue2;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.model.ShipmentModel;
import com.ngen.cosys.validator.annotations.CheckRemarksConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Component
@NoArgsConstructor
@NgenAudit(eventName = NgenAuditEventType.IMPORT_MAIL_CAPTURE_DOC, repository = NgenAuditEventRepository.MAILBAG, entityType = NgenAuditEntityType.MAILBAG)
public class InboundMailDocumentShipmentModel extends ShipmentModel {

	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = 8241056367723472782L;
	@NgenAuditField(fieldName = "Shipment Verification Id")
	private BigInteger shipmentVerificationId;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN_OFFICE_EXCHANGE)
	private String originOfficeExchange;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CATEGORY)
	private String mailCategory;
	private String mailSubCategory;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.YEAR)
	private BigInteger dispatchYear;
	private Boolean registered = Boolean.FALSE;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION_OFFICE_EXCHANGE)
	private String destinationOfficeExchange;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.PIECES)
	private BigInteger breakDownPieces;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHT)
	private BigDecimal breakDownWeight;
	private Boolean updated = Boolean.FALSE;

	@CheckRemarksConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.REMARKS)
	private String remarks;
	private BigInteger flightId;
	
	//Shipment number is mapped as Dispatch number from UI since to avoid any regression impatch changes not ui to correct this.reqired to be revisited once post to neel's availbility. 
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DISPATCH_NUMBER)
	@NgenAuditEntityValue2(parentEntityType = NgenAuditEntityType.MAILBAG, entityType = NgenAuditEntityType.DISPATCH_NUMBER)
	private String shipmentNumber;
	
	private BigInteger totalPieces;
	private BigDecimal totalWeight;
	private boolean shipmentMasterAlreadyCreated;

}