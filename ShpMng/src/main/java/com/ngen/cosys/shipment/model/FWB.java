/**
 * 
 * FWB.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 22 February, 2018 NIIT -
 */

package com.ngen.cosys.shipment.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.awb.validator.AWBDocumentConstraintsValidator;
import com.ngen.cosys.shipment.validators.MaintainFreightWayBillValidator;
import com.ngen.cosys.shipment.validators.SaveAWBDocument;
import com.ngen.cosys.shipment.validators.SearchFWBGroup;
import com.ngen.cosys.validator.annotations.CheckNatureOfGoodsConstraint;
import com.ngen.cosys.validator.annotations.CheckPieceConstraint;
import com.ngen.cosys.validator.annotations.CheckShipmentDestinationConstraint;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.annotations.CheckWeightConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model Class- FWB
 * 
 * @author NIIT Technologies Ltd
 *
 */

@Component
@XmlRootElement
@Scope(scopeName = "prototype")
@ApiModel
@ToString
@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
@Validated
@NgenAudit(entityFieldName = "awbNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_FWB, repository = NgenAuditEventRepository.AWB)
public class FWB extends BaseBO {

   private static final long serialVersionUID = 1L;

   private long shipmentFreightWayBillId;

   private String awbPrefix;

   private String awbSuffix;

   @Size(max = 20, message = "awb.nog.len")
   @NgenAuditField(fieldName = NgenAuditFieldNameType.NATUREOFGOODS)
   @CheckNatureOfGoodsConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups = {
		   MaintainFreightWayBillValidator.class})
   private String natureOfGoodsDescription;

   @JsonSerialize(using = LocalDateSerializer.class)
   @InjectShipmentDate(shipmentNumberField = "awbNumber")
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTDATE)
   private LocalDate awbDate;

   private boolean nonIATA;

   @CheckShipmentNumberConstraint(shipmentNumberField = "awbNumber", groups = {
         SearchFWBGroup.class }, mandatory = MandatoryType.Type.REQUIRED)
   @NotBlank(message = "export.enter.awbnumber", groups = { SearchFWBGroup.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
   private String awbNumber;

   @CheckShipmentDestinationConstraint(groups = {
         MaintainFreightWayBillValidator.class }, mandatory = MandatoryType.Type.REQUIRED)
   @NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN)
   private String origin;

   @CheckShipmentDestinationConstraint(groups = {
         MaintainFreightWayBillValidator.class }, mandatory = MandatoryType.Type.REQUIRED)
   @NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION)
   private String destination;

   @CheckPieceConstraint(groups = { MaintainFreightWayBillValidator.class }, type = MandatoryType.Type.REQUIRED)
   @NgenAuditField(fieldName = NgenAuditFieldNameType.PIECES)
   private BigInteger pieces;

   @NotNull(message = "export.weight.unitcode.required")
   @Size(max = 1, message = "awb.wgt.unit.code")
   @NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHTCODE)
   private String weightUnitCode;

   @CheckWeightConstraint(groups = { MaintainFreightWayBillValidator.class }, mandatory = MandatoryType.Type.REQUIRED)
   @NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHT)
   private BigDecimal weight;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.VOLUME_UNIT_CODE)
   private String volumeUnitCode;

   
   @CheckNatureOfGoodsConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups = {
		   MaintainFreightWayBillValidator.class})
   private String natureOfgoods;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.VOLUME_AMOUNT)
   private BigDecimal volumeAmount;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.DENSITY_INDICATOR)
   private String densityIndicator;

   private long densityGroupCode;

   @JsonSerialize(using = LocalDateSerializer.class)
   //@NgenAuditField(fieldName = NgenAuditFieldNameType.CARRIER_EXE_DATE)
   private LocalDate carriersExecutionDate;
   
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = { MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CARRIER_DESTINATION)
   private String carriersExecutionPlace;
   
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = { MaintainFreightWayBillValidator.class })
   private String carriersExecutionAuthSign;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMS_ORIGIN_CODE)
   private String customOrigin;
   
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = { MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHP_CIRTIFICATION)
   private String shpCertificateSign;

   @Max(value = 5, message = "g.msg.seq.len")
   private int messageSequence;

   @Max(value = 3, message = "g.msg.ver.len")
   private int messageVersion;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime messageProcessedDate;

   private String messageStatus;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHC)
   private List<SHC> shcode;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHT_BOOKING)
   private List<FlightBooking> flightBooking;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.ROUTING)
   private List<Routing> routing;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CONSIGNEE)
   private CustomerInfo consigneeInfo;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPPER)
   private CustomerInfo shipperInfo;

   //@Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.NOTIFY)
   private CustomerInfo alsoNotify;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.ACCOUTING_INFO)
   private List<AccountingInfo> accountingInfo;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.OTHER_CHARGE_DECLARATIONS)
   private ChargeDeclaration chargeDeclaration;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SSR_REMARKS)
   private List<SSROSIInfo> ssrInfo;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.OSI_REMARKS)
   private List<SSROSIInfo> osiInfo;

   @Valid
   @NgenAuditField(fieldName = "Agent Info")
   private AgentInfo agentInfo;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.RATE_DESCRIPTION)
   private List<RateDescription> rateDescription;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.OTH_AENT_CHARGES)
   private List<OtherCharges> otherCharges;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.OTH_AIRLINE_CHARGES)
   private List<OtherCharges> otherChargesCarrier;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.PPD)
   private PrepaidCollectChargeSummary ppd;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.COL)
   private PrepaidCollectChargeSummary col;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENT_REF)
   private ShpReferenceInformation shipmentReferenceInfor;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FWB_NOMI_HANDLING)
   private NominatedHandlingParty fwbNominatedHandlingParty;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.OPI)
   private List<OtherParticipantInfo> otherParticipantInfo;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.OCI)
   private List<OtherCustomsInfo> otherCustomsInfo;

   @Size(max = 15, message = "agent.ard.ref.no.len")
   @NgenAuditField(fieldName = NgenAuditFieldNameType.ARD_AGT_REF)
   private String ardAgentReference;

   private Boolean receivedManuallyFlag;

   private boolean errorFalg;

   private String ovcdReasonCode;

   private String ovcdUserId;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CDC)
   private ChargesDestinationCurrency chargeDestCurrency;

   @NgenAuditField(fieldName = "SenderReference")
   private SenderReference senderReference;

   private BigInteger shipmentId;
   @Range(min=(long) 0.001, max=999999999, message="billing.rate.charge", groups = {MaintainFreightWayBillValidator.class})
   @NgenAuditField(fieldName = "siiChargeAmount")
   private BigDecimal siiChargeAmount;
   @NgenAuditField(fieldName = "cassIndicator")
   private String cassIndicator;

   private Boolean isManifested;

   private BigInteger customerId;

   private Boolean applyCharge = Boolean.FALSE;
   
   private Boolean restrictedAirline=Boolean.FALSE;
}