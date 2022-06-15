package com.ngen.cosys.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement
@ApiModel
@NoArgsConstructor
@Validated
public class MasterAwbDetailModel extends BaseBO {
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private BigInteger customShipmentInfoId;
   private String masterAWBNumber;
   private String agentsCROrIANumber;
   private String cargoAgentCode;
   private String airportCityCodeofOrigin;
   private String airportCityCodeofDestination;
   private String disparityIndicator;
   private BigInteger originalNumberofPackages;
   private BigInteger numberofPackagesinFlight;
   private BigInteger previousFlightNumber;
   private LocalDate previousFlightDate;
   private BigDecimal weight;
   private String exemptionIndicator;
   private String descriptionofGoods;
   private BigInteger originalNoofHAWB;
   private BigInteger numberofHAWBsubmitted;
   private BigInteger noOfPiecesinCMDmessage;
   private String shipperOrConsigneeNameOne;
   private String shipperOrConsigneeNameTwo;
   private String shipperOrConsigneeAddressOne;
   private String shipperOrConsigneeAddressTwo;
}
