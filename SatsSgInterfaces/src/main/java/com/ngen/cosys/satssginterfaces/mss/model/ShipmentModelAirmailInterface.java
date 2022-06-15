package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckAirportCodeConstraint;
import com.ngen.cosys.validator.annotations.CheckCarrierCodeConstraint;
import com.ngen.cosys.validator.annotations.CheckPieceConstraint;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.annotations.CheckWeightConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor
@NgenCosysAppAnnotation
@Validated
public class ShipmentModelAirmailInterface extends BaseBO {

   private static final long serialVersionUID = 1L;

   private BigInteger shipmentId;

   @NotBlank(message = "shipment.number.field.is.mandatory")
   @CheckShipmentNumberConstraint( mandatory = "MandatoryType.Type.NOTREQUIRED")
   @Pattern(regexp = "^[0-9]{1,20}$", message = "invalid.shipmentnumber")
   private String shipmentNumber;

   @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentdate;

   private String receptacleNumber;

   @NotBlank(message = "ERROR_ORIGIN_FIELD_MANDATORY")
   @CheckAirportCodeConstraint()
   private String origin;

   @NotBlank(message = "ERROR_DESTINATION_FIELD_MANDATORY")
   @CheckAirportCodeConstraint()
   private String destination;

   private String shipmentDescriptionCode;
   @NotNull(message = "ERROR_PIECES_FIELD_MANDATORY")
   @CheckPieceConstraint( type = MandatoryType.Type.REQUIRED)

   private BigInteger piece;

   private String weightUnitCode;

   @NotNull(message = "ERROR_WEIGHT_FIELD_MANDATORY")
   @CheckWeightConstraint( mandatory = MandatoryType.Type.REQUIRED)
   private BigDecimal weight;

   private String volumeunitCode;

   private BigDecimal volumeAmount;

   private String densityIndicator;

   private BigInteger densityGroupCode;

   private BigInteger totalPieces;

   @NotBlank(message = "ERROR_NOG_FIELD_MANDATORY")
   private String natureOfGoodsDescription;

   private String movementPriorityCode;

   private String customsOriginCode;

   private String customsReference;

   private Boolean photoCopy = Boolean.FALSE;

   private Boolean barcodePrintedFlag = Boolean.FALSE;

   @CheckCarrierCodeConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
   private String carrierCode;

   private String shcCode;

   @CheckAirportCodeConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
   private String carrierDestination;

   private String shipmentType;

   private String instruction;

   private String bhShipmentNumber;

   @InjectShipmentDate(shipmentNumberField = "bhShipmentNumber")
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate bhShipmentdate;

   private String bhShipmentOrigin;

   private String bhShipmentDestination;

   private LocalDateTime createdOn;

   private String specialHandlingCode;

   private String breakdownId;

   private String hawbNumber;

   private String importArrivalManifestShipmentInfoId;

   private String otherServiceInformation1;

   private String otherServiceInformation2;

   private String transferType;

   private Boolean svc = Boolean.FALSE;

   private Boolean surplusFlag = Boolean.FALSE;
}