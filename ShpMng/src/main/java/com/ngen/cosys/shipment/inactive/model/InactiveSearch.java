package com.ngen.cosys.shipment.inactive.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckRemarksConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
@NgenAudit(entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.INACTIVE_OLD_CARGO, repository = NgenAuditEventRepository.AWB)
public class InactiveSearch extends BaseBO {

	/**
	 * System generated default serial version
	 */
	private static final long serialVersionUID = 1L;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.CARRIER)
	private String carrierCode;

	private int carrierGp;

	private List<String> shcode;

	private String shc;

	private long creationdays;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.LIST_OF_SHIPMENT_DATA)
	private List<ShipmentData> shipmentData;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.REMARKS)
    @CheckRemarksConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
	private String remarks;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.REMARKS)
	private String remarkType;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTTYPE)
	private String shipmentType;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
	private String flightkey;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime flightDate;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
	private String shipmentNumber;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTDATE)
	@JsonSerialize(using = LocalDateSerializer.class)
	private String shipmentDate;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.DELIVERY_ORDER_NO)
	private String deliveryOrderNo;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.LOCAL_AUTHORITY_TYPE)
	private String type;

	private BigInteger deliveryId;

	private BigInteger localAuthorityInfoId;

	private String trmNumber;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime trmDate;
	
	private Boolean continueCustomsSubmission = Boolean.FALSE;

	private boolean partDelivery;
	
	private List<LocalAuthorityDetailsModel> localAuthorityDetail;
	
	private List<BigInteger> inventoryId;
	
	private String origin;
	private String destination;
	private String customerType;
	private String hawbNumber;
	private BigInteger houseId;
	private BigInteger shipmentId;
	private Boolean isHandledByHouse = Boolean.FALSE;
}
