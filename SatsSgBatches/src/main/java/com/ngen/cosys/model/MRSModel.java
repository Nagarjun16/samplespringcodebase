package com.ngen.cosys.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
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
public class MRSModel extends BaseBO {
   /**
   * 
   */
   private static final long serialVersionUID = 1L;
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate flightDate; 
   private String flightKey;
   private String exportOrImport;
   private BigInteger sno;
   private Boolean select;
   @JsonSerialize(using = LocalDateSerializer.class)
   // @InjectShipmentDate(shipmentNumberField = "shipmentNumber" )
   private LocalDate shipmentDate;
   private String shipmentNumber;
   private BigInteger shipmentPiece;
   private BigDecimal shipmentWeight;
   private String shipmentStatus;
   private String origin;
   private String natureOfGoods;
   private String destination;
   private String cmdReceived;
   private String mrsStatusCode;
   private String shipmentstatus;
   private String type;
   private BigInteger customShipmentCustomerInfoId;
   private BigInteger customShipmentLocalAuthorityRequirementId;
   private BigInteger customShipmentInfoId;
   private BigInteger customsFlightId;
   private String awbPrefix;
   private String awbSerialNumber;
   private String cmdProcessingId ;
   private String hwbNumber;
   private BigInteger earliestFlight;
   
}
