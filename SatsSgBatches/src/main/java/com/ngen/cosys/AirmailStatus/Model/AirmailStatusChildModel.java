package com.ngen.cosys.AirmailStatus.Model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "loggedInUser", "flagCRUD", "tenantId",
      "terminal", "userType", "sectorId", "flightId", "shipmentNumber", "shipmentDate" , "shipmentId" ,"scanDateTimeWithoutFormat", "flightDateUnFormatted" })
public class AirmailStatusChildModel extends BaseBO {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   @JacksonXmlProperty(localName = "product")
   private String product;
   @JacksonXmlProperty(localName = "scanType")
   private String eventType;
   @JacksonXmlProperty(localName = "carrierCode")
   private String carrierCode;
   @JacksonXmlProperty(localName = "flightNumber")
   private String flightNumber;
   private LocalDate flightDateUnFormatted;
   @JacksonXmlProperty(localName = "flightDate")
   private String flightDate;
   @JacksonXmlProperty(localName = "containerPou")
   private String containerPou;
   @JacksonXmlProperty(localName = "containerNumber")
   private String containerNumber;
   @JacksonXmlProperty(localName = "containerType")
   private String containerType;
   @JacksonXmlProperty(localName = "containerDestination")
   private String containerDestination;
   @JacksonXmlProperty(localName = "containerPol")
   private String conatinerPol;
   @JacksonXmlProperty(localName = "remarks")
   private String remarks;
   @JacksonXmlProperty(localName = "mailTag")
   private String mailBag;
   @JacksonXmlProperty(localName = "damageCode")
   private String damageCode;
   @JacksonXmlProperty(localName = "damageRemarks")
   private String damageRemarks;
   @JacksonXmlProperty(localName = "offloadReason")
   private String offLoadReason;
   @JacksonXmlProperty(localName = "returnCode")
   private String returnCode;
   @JacksonXmlProperty(localName = "toContainerCode")
   private String toContainerType;
   @JacksonXmlProperty(localName = "toContainer")
   private String toContainer;
   @JacksonXmlProperty(localName = "toCarrierCode")
   private String toCarrierCode;
   @JacksonXmlProperty(localName = "toFlightNumber")
   private String toFlightNumber;
   @JacksonXmlProperty(localName = "toFlightDate")
   private LocalDate toFlghtDate;
   @JacksonXmlProperty(localName = "toContainerPOU")
   private String toContainerPou;
   @JacksonXmlProperty(localName = "toContainerDestination")
   private String toContainerDestination;
   @JacksonXmlProperty(localName = "consignmentDocumentNumber")
   private String consignmentDocumentNumber;
   @JacksonXmlProperty(localName = "serialNumber")
   private int serialNumber;
   @JacksonXmlProperty(localName = "isPABuilt")
   private Boolean isPABuilt;
   @JacksonXmlProperty(localName = "isDelivered")
   private Boolean isDelivered;
   @JacksonXmlProperty(localName = "userName")
   private String userName;
   @JacksonXmlProperty(localName = "scanDateTime")
   private String scanDateTime;
   private LocalDateTime scanDateTimeWithoutFormat;
   private BigInteger flightId;
   private String shipmentNumber;
   private LocalDate shipmentDate;
   private BigInteger shipmentId;
}
