/**
 * 
 * SingPostToApplication.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 3 May, 2018 NIIT -
 */
package com.ngen.cosys.satssg.interfaces.singpost.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This model class represents SingPost To Application (Message Format)
 * 
 * @author NIIT Technologies Ltd
 *
 */
@ApiModel
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "loggedInUser", "flagCRUD", "tenantId",
      "terminal","bookingId", "flightId", "flightBoardPoint", "bookingStatusCode", "shipmentDate", "flightBookingId", 
      "userType", "sectorId", "isValidMb", "flightOffPoint", "origin", "destination", "flightSegmentId", "shipmentNumber" })
@JsonPropertyOrder(value = { "BagStatus", "BagStatusDateTime", "OriginIMPC", "DestinationCountry", "DestinationOE",
      "ServiceType", "DespatchNumber", "BagNumber", "BagWeight", "FlightNumber", "DepartureDateTime" })
@NgenCosysAppAnnotation
public class MailBagResponseModel extends BaseBO {

   private static final long serialVersionUID = 1L;
   
   @JacksonXmlProperty(localName = "DispatchID", isAttribute = true)
   private String dispatchID;
   
   @JacksonXmlProperty(localName = "TotalBagCount", isAttribute = true)
   private String totalBagCount;
  
   @JacksonXmlProperty(localName = "RecpID", isAttribute = true)
   private String recpID;

   @JacksonXmlProperty(localName = "BagStatus")
   private String bagStatus;

   @JacksonXmlProperty(localName = "BagStatusDateTime")
   private String bagStatusDateTime;

   @JacksonXmlProperty(localName = "OriginIMPC")
   private String originIMPC;

   @JacksonXmlProperty(localName = "DestinationCountry")
   private String destinationCountry;

   @JacksonXmlProperty(localName = "DestinationOE")
   private String destinationOE;

   @JacksonXmlProperty(localName = "ServiceType")
   private String serviceType;

   @JacksonXmlProperty(localName = "DespatchNumber")
   private String despatchNumber;

   @JacksonXmlProperty(localName = "BagNumber")
   private String bagNumber;

   @JacksonXmlProperty(localName = "BagWeight")
   private BigDecimal bagWeight;

   @JacksonXmlProperty(localName = "FlightNumber")
   private String flightNumber;

   @JacksonXmlProperty(localName = "DepartureDateTime")
   private String departureDateTime;

   private BigInteger bookingId;
   
   private BigInteger flightId;

   private String flightBoardPoint;

   private String flightOffPoint;

   private String bookingStatusCode;

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentDate;
   
   private BigInteger flightBookingId;
   
   private Boolean isValidMb = Boolean.TRUE;
   
   private String origin;
   
   private String destination;
   
   private BigInteger flightSegmentId;
   
   private String shipmentNumber;
   
   private boolean validFlight;
   
}