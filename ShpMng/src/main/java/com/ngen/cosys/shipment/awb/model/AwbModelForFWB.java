
package com.ngen.cosys.shipment.awb.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.shipment.validators.SearchAWBDocument;
import com.ngen.cosys.validator.annotations.CheckBlackListedShipmentConstraint;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@NgenCosysAppAnnotation
@Validated
public class AwbModelForFWB extends ShipmentModel {
   private String fwb;
   private String fhl;
   private static final long serialVersionUID = 1L;
   private BigInteger shipmentId;
   @CheckBlackListedShipmentConstraint(mandatory = MandatoryType.Type.REQUIRED, groups = { SearchAWBDocument.class })
   @CheckShipmentNumberConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups = { SearchAWBDocument.class })
   private String shipmentNumber;
   @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentdate;
   private String shipmentType;
   private String originOfficeExchange;
   private String mailCategory;
   private String mailSubCategory;
   private String destinationOfficeExchange;

   private BigInteger dispatchYear;
   private Boolean nonIATA = false;;
   private Boolean svc;
   private Boolean partShipment = Boolean.FALSE;
   private Boolean registered = Boolean.FALSE;
   private Boolean manuallyCreated;
   private Boolean console;
   private Boolean fwbe;
   private Boolean fwbm;
   private Boolean fhle;
   private Boolean fhlm;
   private BigInteger flightId;
   private String flightKey;
   private String flightBoardPoint;
   private String flightOffPoint;
   private String flightType;
   private String customerType;
   private Boolean docRecieved;
   private Boolean copyAwb;
   private Boolean pouchRecieved;
   private String ovcdReasonCode;
   private String chargeCode;
   private String eawb;
   private BigInteger irregularityPieces;
   private BigDecimal irregularityWeight;
   private String caseNumber;
   private BigInteger actualPieces;
   private BigDecimal actualWeight;
   private BigInteger oldPieces;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime flightDate;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime documentReceivedOn;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime documentPouchReceivedOn;
   private ShipmentMasterCustomerInfo consignee;
   private ShipmentMasterCustomerInfo shipper;
   private ShipmentMasterCustomerInfo notify;
   @Valid
   private ShipmentOtherChargeInfoFWB otherChargeInfo;
   private ShipmentMasterHandlingArea handlingArea;
   private ShipmentMasterLocalAuthorityInfo localAuthority;
   @Valid
   private List<ShipmentMasterRoutingInfo> routing;
   @Valid
   private List<ShipmentMasterShc> shcs;
   private List<ShipmentMasterShcHandlingGroup> shcHandlingGroup;
   private List<ShipmentRemarksModel> ssrRemarksList;
   private List<ShipmentRemarksModel> osiRemarksList;
   private BigDecimal oldWeight;
   private Boolean isExport = Boolean.FALSE;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime photoCopyReceivedOn;

}
