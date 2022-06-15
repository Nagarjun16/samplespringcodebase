package com.ngen.cosys.satssg.singpost.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This model class represents Application To SingPost (Message Format)
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "loggedInUser", "flagCRUD", "tenantId",
      "terminal", "userType", "sectorId","shipmentId","shipmentNumber", "carrierCode", "versionDateTime" })
@JsonPropertyOrder(value = { "bagStatus", "bagStatusDateTime", "bagConditionCode", "flightNumber",
      "flightTouchDownDateTime" })
public class FlightTouchDown extends BaseBO {

   private static final long serialVersionUID = 1L;

   @JacksonXmlProperty(localName = "RecpID", isAttribute = true)
   private String recpID;

   @JacksonXmlProperty(localName = "BagStatus")
   private String bagStatus;

   @JacksonXmlProperty(localName = "BagStatusDateTime")
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime bagStatusDateTime;

   @JacksonXmlProperty(localName = "BagConditionCode")
   private String bagConditionCode;

   @JacksonXmlProperty(localName = "FlightNo")
   private String flightNumber;

   @JacksonXmlProperty(localName = "FlightTouchDownDateTime")
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime flightTouchDownDateTime;

   private BigInteger shipmentId;
   private String shipmentNumber;
   private String carrierCode;
}
