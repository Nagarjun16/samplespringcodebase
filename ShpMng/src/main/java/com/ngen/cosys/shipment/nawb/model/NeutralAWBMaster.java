/**
 * 
 * NeutralAWBMaster.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 29 January, 2018 NIIT -
 */
package com.ngen.cosys.shipment.nawb.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.model.AccountingInfo;
import com.ngen.cosys.shipment.model.AgentInfo;
import com.ngen.cosys.shipment.model.CCCharges;
import com.ngen.cosys.shipment.model.ChargeDeclaration;
import com.ngen.cosys.shipment.model.CustomerInfo;
import com.ngen.cosys.shipment.model.FlightBooking;
import com.ngen.cosys.shipment.model.NeutralAwbCustoms;
import com.ngen.cosys.shipment.model.NominatedHandlingParty;
import com.ngen.cosys.shipment.model.OtherCharges;
import com.ngen.cosys.shipment.model.OtherCustomsInfo;
import com.ngen.cosys.shipment.model.OtherParticipantInfo;
import com.ngen.cosys.shipment.model.PrepaidCollectChargeSummary;
import com.ngen.cosys.shipment.model.RateDescOtherInfo;
import com.ngen.cosys.shipment.model.RateDescription;
import com.ngen.cosys.shipment.model.Routing;
import com.ngen.cosys.shipment.model.SHC;
import com.ngen.cosys.shipment.model.SSROSIInfo;
import com.ngen.cosys.shipment.model.SenderReferenceInformation;
import com.ngen.cosys.shipment.model.ShpReferenceInformation;
import com.ngen.cosys.shipment.nawb.validator.NeutralAWBValidatorGroup;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This Model Class takes care of the Neutral AWB Master.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@XmlRootElement
@ApiModel
@Component
@Setter
@Getter
@ToString
@NoArgsConstructor
@Validated
@NgenAudit(entityType = NgenAuditEntityType.AWB, entityFieldName = "awbNumber", eventName = NgenAuditEventType.NAWB_MANAGEMENT, repository = NgenAuditEventRepository.AWB)
public class NeutralAWBMaster extends BaseBO {
   private static final long serialVersionUID = 1L;
   private BigInteger shipmentFreightWayBillId;
   private BigInteger neutralAWBId;
   private BigInteger sidHeaderId;
   @NgenAuditField(fieldName = "Sid Number")
   private String sidNumber;
   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CONSIGNEE)
   private CustomerInfo consigneeInfo;
   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPPER)
   private CustomerInfo shipperInfo;
   @Valid
   @NgenAuditField(fieldName = "Agent Info")
   private AgentInfo agentInfo;
   // @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHCS)
   private List<SHC> shcCode;
   // @Valid
   private Routing routing;
   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.ACCOUTING_INFO)
   private List<AccountingInfo> accountingInfo;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.OCI)
   private List<OtherCustomsInfo> otherCustomsInfo;
   @Valid
   @NgenAuditField(fieldName = "NAWB Customs")
   private NeutralAwbCustoms neutralAwbCustoms;

   // @Valid
   @NgenAuditField(fieldName = "Rate Description Other Info")
   private List<RateDescOtherInfo> rateDescriptionOtherInfo;
   // @Valid
   private List<RateDescription> rateDescription;

   @NgenAuditField(fieldName = "Prepaid Charge Summary")
   private List<PrepaidCollectChargeSummary> ppdCol;
   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.OTHER_CHARGE_DECLARATIONS)
   private ChargeDeclaration chargeDeclaration;
   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SSR_OSI_REMARKS)
   private List<SSROSIInfo> ssrOsiInfo;

   @NgenAuditField(fieldName = "Other Charges Due Agent")
   private List<OtherCharges> otherChargesDueAgent;

   @NgenAuditField(fieldName = "Other Charges Due carrier")
   private List<OtherCharges> otherChargesDueCarrier;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.PPD)
   private PrepaidCollectChargeSummary ppd;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.COL)
   private PrepaidCollectChargeSummary col;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENT_REF)
   private ShpReferenceInformation shipmentReferenceInfo;
   @NgenAuditField(fieldName = "Sender Reference Information")
   private SenderReferenceInformation senderReferenceInfo;
   @NgenAuditField(fieldName = "Collection Charges")
   private CCCharges ccCharges;
   @NgenAuditField(fieldName = "Nominated Handling Party")
   private NominatedHandlingParty nawbNominatedHandlingParty;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.OPI)
   private List<OtherParticipantInfo> otherParticipantInfo;
   // @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHT_BOOKING)
   private FlightBooking flightBooking;
   @Valid
   @NgenAuditField(fieldName = "Flight Booking List")
   private List<FlightBooking> flightBookingList;
   private String awbPrefix;
   private String awbSuffix;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
   private String awbNumber;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTDATE)
   @JsonSerialize(using = LocalDateSerializer.class)
   @InjectShipmentDate(shipmentNumberField = "awbNumber")
   private LocalDate awbDate;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN)
   @NotEmpty(message = "g.required", groups = { NeutralAWBValidatorGroup.class })
   private String origin;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION)
   @NotEmpty(message = "g.required", groups = { NeutralAWBValidatorGroup.class })
   private String destination;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.PIECES)
   @NotNull(message = "g.required", groups = { NeutralAWBValidatorGroup.class })
   private BigInteger pieces;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHTCODE)
   @NotEmpty(message = "g.required", groups = { NeutralAWBValidatorGroup.class })
   private String weightUnitCode;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHT)
   @NotNull(message = "g.required", groups = { NeutralAWBValidatorGroup.class })
   private BigDecimal weight;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.NATUREOFGOODS)
   //@CheckNatureOfGoodsConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups = NeutralAWBValidatorGroup.class)
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = {NeutralAWBValidatorGroup.class})
   private String natureOfGoodsDescription;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.VOLUME_UNIT_CODE)
   private String volumeUnitCode;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.VOLUME_AMOUNT)
   private double volumeAmount;
   private String densityIndicator;
   private int densityGroupCode;
   private String shippersCertificateSignature;
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate carriersExecutionDate;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CARRIER_EXECUTIONPLACE)
   private String carriersExecutionPlace;
   private String carriersExecutionAuthorisationSignature;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMS_ORIGIN_CODE)
   private String customOrigin;
   private String commissionInformationCASSIndicator;
   private double commissionInformationCommissionAmount;
   private double salesIncentiveDetailCommissionPercentage;
   private String salesIncentiveDetailCASSIndicator;
   @NgenAuditField(fieldName = "Endorsed By")
   private String endorsedBy;
   @NgenAuditField(fieldName = "Endorsed For")
   private String endorsedFor;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime endorsedOn;
   @NgenAuditField(fieldName = "Handling Area")
   private String handlingArea;
   @NgenAuditField(fieldName = "Status")
   private String status;
   @NgenAuditField(fieldName = "Handling Information")
   private String handlingInformation;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.ARD_AGT_REF)
   private String ardAgentReference;
   @NgenAuditField(fieldName = "Request Number")
   private String requestNumber;
   private String shipmentNumber;

   @NgenAuditField(fieldName = "SVC")
   private Boolean svc;
   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.ROUTING)
   private List<Routing> routingList;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.NOTIFY)
   private CustomerInfo alsoNotify;
   private boolean flagError;
   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.RATE_DESCRIPTION)
   private List<RateDescription> rateDescriptionForNawb;
   
   private Boolean isAwbReservation;
   
   private String stockCategoryCode = "GEN";
}
