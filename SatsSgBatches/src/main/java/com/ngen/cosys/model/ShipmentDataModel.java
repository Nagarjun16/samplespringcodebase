package com.ngen.cosys.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

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
public class ShipmentDataModel extends BaseBO {
   /**
   * 
   */
   private static final long serialVersionUID = 1L;
   // @InjectShipmentDate(shipmentNumberField = "shipmentNumber" )
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentDate;
   private String shipmentNumber;
   private BigInteger shipmentId;
   private String origin;
   private String destination;
   private String exportOrImport;
   private int partShipment;
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate flightDate;
   private String flightKey;
   private BigInteger customsFlightId;
   private BigInteger customShipmentInfoId;
   private BigInteger shipmentCustomerInfoId;
   private BigInteger pieces;
   private BigDecimal weight;
   private BigInteger shipmentPiece;
   private BigDecimal shipmentWeight;
   private String natureOfGoodsDescription;
   private String mrsStatusCode;
   private int partShipmentFlag;
   private String shipmentStatus;
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate cmdRecievedDate;
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate mrsSentDate;
   private List<LocalAuthorityModel> localAuthorityinfo;
   private List<CustomerModel> customerList;
   private String awbPrefix;
   private String awbSerialNumber;
   private String cmdProcessingId ;
   private String hwbNumber;
   private BigInteger earliestFlight;
   private String localAuthorityType;
   private String flightCancelFlag;
   private BigInteger manuallyUpdated;
   private String deliveryRequestOrderNo;
   private String deliveryOrderNo;
   private String poNumber;
   private String cancellationReasonCode;
   private BigInteger customShipmentLocalAuthorityRequirementId;
   private BigInteger customShipmentLocalAuthorityRequirementDetailId;
   private BigInteger customShipmentCustomerInfoId;
   private BigInteger customFlightId;
   private String importExportIndicator;
   private String  irregularity;
   private String customerName;
   private BigInteger shipmentMasterLocalAuthInfoId;
}
