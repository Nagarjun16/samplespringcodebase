package com.ngen.cosys.impbd.mail.manifest.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEntityValue3;
import com.ngen.cosys.audit.NgenAuditEntityValue4;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.model.FlightModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@Validated
//@NgenAudit(eventName = NgenAuditEventType.AIRMAIL_IMPORT, repository = NgenAuditEventRepository.MAIL_BREAKDOWN, entityFieldName = "Shipment Id", entityType = NgenAuditEntityType.AIRMAIL_BREAKDOWN)
@NgenAudit(entityFieldName = NgenAuditFieldNameType.FLIGHTKEY, entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.MAIL_IMP_MAIL_MANIFEST_DOC_COMPLETE, repository = NgenAuditEventRepository.MAILBAG)
@NgenAudit(entityFieldName = NgenAuditFieldNameType.FLIGHTKEY, entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.MAIL_IMP_MAIL_MANIFEST_BD_COMPLETE, repository = NgenAuditEventRepository.MAILBAG)
public class InboundMailManifestModel extends FlightModel {

	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	@NgenAuditEntityValue3(parentEntityType = NgenAuditEntityType.MAILBAG, entityType = NgenAuditEntityType.FLIGHT)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
	private String flightKey;
	@NgenAuditEntityValue4(parentEntityType = NgenAuditEntityType.MAILBAG, entityType = NgenAuditEntityType.FLIGHT)
    @NgenAuditField(fieldName = NgenAuditFieldNameType.FLT_DATE)
    private LocalDate flightDate;
	private String destination;
	@NgenAuditField(fieldName = "Breakdown Location")
	private String breakDownLocation;
	private String breakDownUld;
    private String breakDownPieces;
	private String breakDownWeight;
	@NgenAuditField(fieldName = "Shipment Id")
	private BigInteger segmentId;
	private Boolean checkData = Boolean.FALSE;
	@NgenAuditField(fieldName = "First BreakDown Complete")
	   private Boolean mailFirstTimeBreakDownCompletedBy;
	@NgenAuditField(fieldName = "First Document Complete")
	   private Boolean mailFirstTimeDocumentVerificationCompletedBy;
	@NgenAuditField(fieldName = "Breakdown Completed By")
	   private String breakDownCompletedBy;
	@NgenAuditField(fieldName = "Breakdown Completed At")
	   private LocalDateTime breakDownCompletedAt;
	@NgenAuditField(fieldName = "Document Completed By")
	   private String documentCompletedBy;
	@NgenAuditField(fieldName = "Document Completed At")
       private LocalDateTime documentCompletedAt;
	   private List<InboundMailManifestShipmentInfoModel> inboundShipments;
	   @Valid
	private List<InboundMailManifestShipmentInfoModel> shipments;
	   private List<MailBagInformation> mailBagInfo;
	   private String segments;
	   private String nextDestination;
	   private String shipmentLocation;

}