package com.ngen.cosys.shipment.house.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityRef6;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEntityValue6;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.house.validator.HouseWayBillValidationGroup;
import com.ngen.cosys.shipment.house.validator.MAWBValidationGroup;
import com.ngen.cosys.validator.annotations.CheckNatureOfGoodsConstraint;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.annotations.UserCarrierValidation;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This model class HAWBNumberForGivenAWB entity to show all HAWB Number which
 * belongs to given AWB Number and related all details
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@XmlRootElement
@Setter
@Getter
@NoArgsConstructor
@Validated
@NgenCosysAppAnnotation
//@UserCarrierValidation(shipmentNumber = "awbNumber", flightKey = "", loggedInUser = "loggedInUser", type = "AWB",groups = {MAWBValidationGroup.class })
//hawb record audit
@NgenAudit(eventName = NgenAuditEventType.MAINTAIN_HWB_LIST, repository = NgenAuditEventRepository.AWB, entityFieldName = "AWB Number", entityType = NgenAuditEntityType.AWB)
@NgenAudit(eventName = NgenAuditEventType.MAINTAIN_HOUSE, repository = NgenAuditEventRepository.AWB, entityFieldName = "awbNumber", entityType = NgenAuditEntityType.AWB)
public class MasterAirWayBillModel extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private BigInteger id;

	private BigInteger houseId;

	/**
	 * AWBNumber of the flight for tracking HAWBNumber And related details
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.AWB_NUMBER)
	@CheckShipmentNumberConstraint(groups = { MAWBValidationGroup.class }, mandatory = MandatoryType.Type.REQUIRED)
	@NotBlank(message = "export.awb.number.required", groups = { MAWBValidationGroup.class })
	private String awbNumber;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.AWB_DATE)
	@JsonSerialize(using = LocalDateSerializer.class)
	@InjectShipmentDate(shipmentNumberField = "awbNumber")
	private LocalDate awbDate;

	/**
	 * HAWBNumber
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.HAWB_NUMBER)
	@NgenAuditEntityValue6(entityType = NgenAuditEntityType.HAWB, parentEntityType = NgenAuditEntityType.AWB)
	@NgenAuditEntityRef6(entityType = NgenAuditEntityType.HAWB,  parentEntityType = NgenAuditEntityType.AWB)
	private String hawbNumber;

	@NgenAuditField(fieldName = "awb Prefix")
	private String awbPrefix;

	@NgenAuditField(fieldName = "awb Suffix")
	private String awbSuffix;

	/**
	 * origin of the flight for shipment
	 */
	 @NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN)
	private String origin;
	
	private int sNo;

	@NgenAuditField(fieldName = "Shc")
	private String shc;
	/**
	 * destination of the flight for shipment
	 */
	 @NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION)
	private String destination;

	/**
	 * number of pieces
	 */
	 @NgenAuditField(fieldName = NgenAuditFieldNameType.PIECES)
	private BigInteger pieces;

	/**
	 * weight
	 */
	 @NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHT)
	private BigDecimal weight;
	/**
	 * weightCode is for measurment
	 */
	private String weightUnitCode;
	/**
	 * total number of HWB which belongs to particular AWB number
	 */
	@NgenAuditField(fieldName = "Total HWB")
	private BigInteger totalHWB;
	/**
	 * total number of pieces which belongs to particular AWB number
	 */
	@NgenAuditField(fieldName = "Total Pieces")
	private BigInteger totalPieces;
	/**
	 * total Weight which belongs to particular AWB number
	 */
	@NgenAuditField(fieldName = "Total Weight")
	private BigDecimal totalWeight;

	/*
	 * House List for a given AWB
	 */
	@NgenAuditField(fieldName = "Maintain House Detail List")
	private List<HouseModel> maintainHouseDetailsList;

	/*
	 * Model used for establishing CRUD operation for individual house
	 */
	@NgenAuditField(fieldName = "House Model")
	private HouseModel houseModel;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CHARGEABLE_WEIGHT)
	private BigDecimal chargeableWeight;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.NATURE_OF_GOODS)
	@CheckNatureOfGoodsConstraint(mandatory = MandatoryType.Type.REQUIRED, groups = {
			HouseWayBillValidationGroup.class })
	@Size(max = 20, message = "Nature Of Goods Description max 20 charater")
	private String natureOfGoods;

    @NgenAuditField(fieldName = "handledByHouse")
    private String handledByHouse;

	private boolean firstTimeSave;
	private boolean restrictedAirline;
	
	private String intDom;
	private String terminalHandling;
	
	private String handledByDOMINT;
	private String handledByMasterHouse;
	
    //for hawb list
	@NgenAuditField(fieldName = NgenAuditFieldNameType.LIST_OF_SHC)
	private List<String> shcList;
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate deliveredOn;
	@NgenAuditField(fieldName = "Total Chargeable Weight")
	private BigDecimal totalChargeableWeight;
	private boolean doFlag = Boolean.FALSE;
    private boolean poFlag = Boolean.FALSE;
	private String appointedAgentCode;
	

}