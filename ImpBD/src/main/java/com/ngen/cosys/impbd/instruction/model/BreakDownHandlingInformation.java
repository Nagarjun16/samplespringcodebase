package com.ngen.cosys.impbd.instruction.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckNatureOfGoodsConstraint;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Validated
@NgenCosysAppAnnotation
@NgenAudit(entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CARGO_BREAKDOWN_HANDLING_INFORMATION, repository = NgenAuditEventRepository.AWB)
public class BreakDownHandlingInformation extends BaseBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger breakdownId;
	private Long flightId;

	@NgenAuditField(fieldName = "Shipment Number")
	@CheckShipmentNumberConstraint(mandatory = MandatoryType.Type.REQUIRED)
	private String shipmentNumber;
	@InjectShipmentDate(shipmentNumberField = "shipmentNumber")
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate shipmentDate;
	@NotBlank(message = "ERROR_ORIGIN_IS_BLANK")
	@NotNull(message = "ERROR_ORIGIN_IS_BLANK")
	private String origin;
	@NotBlank(message = "ERROR_DESTINATION_IS_BLANK")
	@NotNull(message = "Destination  is blank")
	private String destination;

	private Short pieces;
	@NotNull(message = "ERROR_BREAKDOWN_INSTRUCTION_IS_BLANK")
	@Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters")
	private String instruction;
	@CheckNatureOfGoodsConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
	private String natureOfGoodsDescription;
	private String weight;
	private String hawbNumber;

	private String shc;
	private String flagCRUD = "R";
	private List<HouseListModel> houseNumberList;
	private String authorizationId;
	private String sourceId;
	private String contains;
	private String code;
	private String desc;
	private String param1;
	private String param2;
	private String param4;
	private String param5;
	private String param6;
	private String param7;
	private String deckFlag;
	private boolean noCache;
	private String parameter1;
	private String parameter2;
	private String parameter3;
}