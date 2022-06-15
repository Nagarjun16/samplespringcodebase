package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentHouse extends BaseBO {
   /**
    * Default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   private BigInteger shipmentInventoryId;
   private String type;
   private String number;
   private BigInteger pieces;
   private BigDecimal weight;
   private boolean damage;
   private BigInteger loadedShipmentInfoId;
   private BigInteger shipmentId;
   private BigInteger shipmentHouseId;
   private String mailbagNumber;
   private String receptacleNumber;
   private String dispatchSeries;
   private String originCountry;
   private String originAirport;
   private String originQualifier;
   private String destinationCountry;
   private String destinationAirport;
   private String destinationQualifier;
   private String category;
   private String subCategory;
   private String year;
   private String dispatchNumber;
   private String receptableNumber;
   private String lastBagIndicator;
   private String registeredInsuredFlag;
   private String referenceId;
}