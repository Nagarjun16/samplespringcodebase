package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckContainerNumberConstraint;
import com.ngen.cosys.validator.annotations.CheckHousewayBillConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@XmlRootElement
@Component
@ToString
@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
@Validated
@CheckHousewayBillConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, shipmentTypeField = "shipmentType", hwbNumberField = "mailBagNumber")
public class BuildUpMailSearch extends BaseBO {
   /**
    * Default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   @CheckContainerNumberConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
   private String uldNumber;
   private BigInteger flightId;
   private String flightKey;
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate flightOriginDate;
   private String flightOffPoint;
   private BigInteger segmentId;
   private String carrierCode;
   private List<String> mailbagId;
   private String mailBagNumber;
   private String shipmentType;
   private String shipmentNumber;
   private BigInteger shipmentId;
   private BigInteger shipmentInventoryId;
   private BigInteger pieces;
   private BigDecimal weight;
   private String entityKey;
   private String destination;
   private boolean dlsCompleted;
   private List<ShipmentHouse> mailBags;
   /*private List<ShipmentMailBagModel> preBookedList;
   private List<ShipmentMailBagModel> containerList;
   private List<ShipmentMailBagModel> dispatchList;*/
}