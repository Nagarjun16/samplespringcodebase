package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
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
@NgenCosysAppAnnotation
@Validated
public class ShipmentMailBagModel extends BaseBO {
   /**
    * Default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   private BigInteger assUldTrolleyId;
   private BigInteger shipmentId;
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
   private BigDecimal weight;
   private String location;
   private boolean check;
   private BigInteger pieces;
   private List<ShipmentHouse> mailbags;
}