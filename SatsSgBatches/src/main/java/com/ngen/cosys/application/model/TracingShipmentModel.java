package com.ngen.cosys.application.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@XmlRootElement
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TracingShipmentModel extends BaseBO {

   /**
    * Default serial version id
    */
   private static final long serialVersionUID = 1L;

   private String shipmentNumber;
   private String groupCode;
   private BigInteger shipmentId;
   private Integer cycleOne;
   private Integer cycleTwo;
   private Integer cycleThree;
   private Integer cycleFour;
   private Integer moveToAbandoned;
   private String currentSlab;
   private LocalDateTime sta;
   private String activity;
   private BigInteger comTracingShipmentInfoId;
   private String flightKey;
   private String boardPoint;
   private String offPoint;
   private BigInteger shpPieces;
   private BigDecimal shpWeight;
   private BigInteger invPieces;
   private BigDecimal invWeight;
   private String caseNumber;
   private String caseStatus;
   private String origin;
   private String destination;
   private LocalDateTime shipmentDate;
   private String irregularityTypeCode;
   private String shipmentType;
   private String natureOfGoods;
   private LocalDateTime shipmentDocumentDate;
   private BigInteger comTracingShipmentFollowupActionId;
   private String tracingShipmentInfoInsertIndicator;
   private String followupActionInsertIndicator;
   private String tracingShipmentInfoUpdateIndicator;
   private String newStatus;
   private String agentCode;
   private String carrierCode;

   private List<TracingShipmentModelSHC> shcArray;
   private String shcCodes;
	private String houseNumber;
   private List<TracingShipmentModel> hawbNumber;
}
