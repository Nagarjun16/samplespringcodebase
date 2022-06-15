package com.ngen.cosys.impbd.flightdiscrepancylist.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@ApiModel
@ToString
@Setter
@Getter
@NoArgsConstructor
public class FlightDiscrepancyList extends BaseBO {
   private static final long serialVersionUID = 1L;
   private boolean select;
   private BigInteger segmentId;
   private String segment;
   private BigInteger shipmentId;
   private String shipmentNumber;
   private LocalDate shipmentDate;
   private String origin;
   private String destination;
   private BigInteger manifestPieces;
   private BigDecimal manifestWeight;
   private BigInteger awbPieces;
   private BigDecimal awbWeight;
   private BigInteger breakDownPieces;
   private BigDecimal breakDownWeight;
   private String shcs;
   private String irregularity;
   private String irregularityTypeDescription;
   private BigInteger flightId;
   private String shipmentType;
   private int sno;
   private List<BigInteger> shipmentIrregularityIds; 
   private BigInteger fdlSentFlag;
   private BigInteger flightSegmentId;
   private String cargoIrregularityCode;
}