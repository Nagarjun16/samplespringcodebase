package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DLSULDTrolley extends BaseBO {

   /**
    * Default serial version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger dlsUldTrolleyId;// DLSId
   private BigInteger loadingControlAdviceId;
   private BigInteger plannedFlightId;
   private BigInteger dlsId;
   private BigInteger ucmId;
   private BigInteger ucmFlightId;
   private BigInteger containerCount;

   @NotNull(message = "g.required")
   private BigInteger flightSegmentId;// flightSegmentId

   @NotEmpty(message = "g.required")
   private String uldTrolleyNumber;// ULDNumber

   private BigDecimal estimatedWeight;
   private BigDecimal tareWeight;
   private BigDecimal dryIceWeight;
   private BigDecimal actualWeight;
   private BigDecimal manifestWeight;
   private BigDecimal icsGrossWeight;
   private BigDecimal netWeight;
   private BigDecimal weightDifference;
   
   @Range(min = 1, max = 999, message = "export.priority.range.validation")
   private Integer priorityOfLoading;

   private String heightCode;

   private String transferType;
   private String usedForExpressCourierContainer;
   private String remarks;
   private String piggybackString;
   private String accessory;
   private String flightKey;
   private BigInteger flightId;
   private String content;
   private String loadingControlAdvice;
   private String shc;
   private String bupUnitType;
   private String tagUnitType;
   private String handlingCode;
   private String overhangingInd;
   private String uldType;
   private String uldCarrierCode;
   private String palletTypeFlag;
   private String validUld;
   private String handlingArea;
   private String destination;

   private Boolean usedForPerishableContainer = Boolean.FALSE;
   private Boolean usedForTransitUse = Boolean.FALSE;
   private Boolean usedAsTrolley = Boolean.FALSE;
   private Boolean usedAsStandBy = Boolean.FALSE;
   private Boolean rampHandover = Boolean.FALSE;
   private Boolean manual = Boolean.FALSE;
   private Boolean piggyback = Boolean.FALSE;
   private Boolean trolleyInd = Boolean.FALSE;
   private Boolean icsULD = Boolean.FALSE;

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate flightOriginDate;

  // private List<DLSNFMRoute> routeList;

   @NotNull(message = "content.required")
   private List<String> contentCode;

}