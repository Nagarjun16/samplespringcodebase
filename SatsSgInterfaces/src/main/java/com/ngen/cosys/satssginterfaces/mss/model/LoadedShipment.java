package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

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
public class LoadedShipment extends BaseBO {

   /**
    * serialVersionUID
    */
   private static final long serialVersionUID = 1L;
   private String shipmentNumber; 
   private String flightKey;
   private BigInteger flightId;

   private String assUldTrolleyNo; //
   private BigInteger assUldTrolleyId; //
   private String contentCode;
   private String heightCode;
   private Integer phcIndicator;

   private String uldType;
   private String uldCarrierCode;
   private String uldCarrierCode2;
   
   private String number;

   private String segment;
   private BigInteger segmentId;
   private BigDecimal dryIceWeight = BigDecimal.valueOf(0.0);
   private BigDecimal moveDryIce= BigDecimal.valueOf(0.0);
   private BigInteger shipmentId;
   private BigInteger shipmentInventoryId;
   private BigInteger locationPiecs; //
   private BigDecimal locationWeight; //
   private BigInteger loadedShipmentInfoId;
   private BigInteger loadedPieces;
   private BigDecimal loadedWeight;
   private BigInteger movePiecs; //
   private BigDecimal moveWeight; //
   private String tagInfo;
   private String shcInfo;
   private String shcs;
   private String tagNo;
   private boolean errorFlag;
   
   private boolean mailBagFlag = Boolean.FALSE;

   private List<String> shcList; //
   private List<String> tagNumberList;
   private List<ShipmentHouse> shipHouseList; //
   private List<SHCS> loadedShcList;
   private String natureOfGoodsDescription;
   
   // Properties for creating Inventory
   private String shipmentLocation;
   private String handlingArea;
   private BigInteger weighingId;
   private BigDecimal actuvalWeightWeighed;
   private String warehouseLocation;
   
   // Properties for calculating Actual Weight Weighed
   private BigInteger intialLocationPieces;
   private BigDecimal intialLocationWeight;
   
   private String link;
}
