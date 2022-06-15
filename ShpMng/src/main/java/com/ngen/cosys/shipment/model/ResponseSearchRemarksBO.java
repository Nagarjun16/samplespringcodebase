package com.ngen.cosys.shipment.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the Base class for all the AWB Shipment List.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSearchRemarksBO extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   /**
    * This field contains AWB Id
    */
   private long shipmentId;

   /**
    * This field contains AWB Number
    */
   @NotBlank(message = "export.enter.awbnumber")
   private String shipmentNumber;

   private Boolean existRemarks = Boolean.FALSE;

   /**
    * The AWB level remarks
    */
   private List<MaintainRemark> noFlightRemarks;
   /**
    * The Shipment remarks for a given flight
    */
   private List<FlightAssociatedRemarksBO> flightBasedRemarks;
   private String origin;
   private String destination;
   private BigInteger pieces;
   private BigDecimal weight;
   private String natureOfGoods;
   private List<SHC> specialHandlingCode;
   private List<String> specialHandlingCodeHAWB;
   private String hawbNumber;
   private ShipmentHouseInformation shipmentHouseInfo;
   private List<MaintainRemark> hwbRemarks;
   
  
   
   
}