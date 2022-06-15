/**
 * Object which holds all information related shipments and it's associated flight/customer information
 */
package com.ngen.cosys.impbd.events.payload;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.NOA_SENT_TO, repository = NgenAuditEventRepository.AWB)
public class InboundShipmentInfoModel extends BaseBO {

	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = 1L;

	private BigInteger shipmentId;
	private BigInteger flightId;
	private BigInteger pieces;
	private BigInteger shipmentPieces;

	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.SHIPMENTNUMBER)
	private String shipmentNumber;
	private String flightKey;
	private String customerName;
	private String customerCode;
	private String origin;
	private String destination;
	private String consigneeName;

	private Boolean documentReceived = Boolean.FALSE;

	private BigDecimal weight;
	private BigDecimal shipmentWeight;
	private BigDecimal charges;

	private String houseNumber;
	private BigInteger housePieces;
	private BigDecimal houseWeight;

	private LocalDate shipmentDate;
	private LocalDate flightDate;
	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.EMAIL)
	private String emailAddresses;

	private List<InboundShipmentInfoModel> shipments;
}
