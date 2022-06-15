/**
 * DCSFMUpdateCargoFiguresReply.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.model;

import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class is used for Departure Control FM Cargo Figure request
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "eventSource", "tenant", "flightId" })
@JsonPropertyOrder(value = { "flightNumberInformation", "flightDate", "legOrigin", "flightLegDate", "sourceSystemInfo",
      "acTypeAndReg", "agentNameForNOTOC", "cargoAgentNameSeparator", "cargoAgentName", "workStationAndPrinter",
      "phoneAndFax", "dwsComments", "indicators", "dispatchTime", "loadInfo" })
@JacksonXmlRootElement(localName = "DCSFM_UpdateCargoFigures", namespace = "http://xml.amadeus.com/FECFUQ_17_1_1A")
public class DCSFMUpdateCargoFigures {

   /**
    * Used for internally
    */
   private String eventSource;
   private String tenant;
   private BigInteger flightId;
   
   /**
    * Carrier, Flight and Suffix - (M - 1 time)
    */
   @JacksonXmlProperty(localName = "flightNumberInformation")
   private FlightNumberInformation flightNumberInformation;
   
   /**
    * Flight Date - (C - 2 times)
    */
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "flightDate")
   private List<FlightDate> flightDate;
   
   /**
    * Departure port of the Leg - (M - 1 time)
    */
   @JacksonXmlProperty(localName = "legOrigin")
   private LegOrigin legOrigin;
   
   /**
    * Departure date of the leg - (C - 2 times)
    */
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "flightLegDate")
   private List<FlightDate> flightLegDate;
   
   /**
    * Source system info - (M - 1 time)
    */
   @JacksonXmlProperty(localName = "sourceSystemInfo")
   private SourceSystemInfo sourceSystemInfo;
   
   /**
    * Aircraft Type info - (M - 1 time)
    */
   @JacksonXmlProperty(localName = "acTypeAndReg")
   private AircraftTypeRegistration aircraftTypeRegistration;
   
   /**
    * Send Notoc Agent First & Last Name - (C - 1 time)
    */
   @JacksonXmlProperty(localName = "agentNameForNOTOC")
   private AgentNameForNOTOC agentNameForNOTOC;
   
   /**
    * Dummy Segment - (M - 1 time)
    */
   @JacksonXmlProperty(localName = "cargoAgentNameSeparator")
   private CargoAgentNameSeparator cargoAgentNameForSeparator;
   
   /**
    * Agent First & Last Name - (C - 1 time)
    */
   @JacksonXmlProperty(localName = "cargoAgentName")
   private CargoAgentName cargoAgentName;
   
   /**
    * Work station and printer address - (C - 1 time)
    */
   @JacksonXmlProperty(localName = "workStationAndPrinter")
   private WorkStationAndPrinter workStationAndPrinter;
   
   /**
    * Cargo Agent Telephone and Fax - (C - 2 times)
    */
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "phoneAndFax")
   private List<PhoneAndFax> phoneAndFax;
   
   /**
    * Any deadload weight statement comments - (C - 1 time)
    */
   @JacksonXmlProperty(localName = "dwsComments")
   private DWSFreeTextQualifier dwsComments;
   
   /**
    * Status Detail DSP/DSF - Provisional/Final Deadload Weight Statement) 
    *   - (C - 1 time)
    */
   @JacksonXmlProperty(localName = "indicators")
   private Indicator indicator;
   
   /**
    * Dispatch Time - (M - 1 time)
    */
   @JacksonXmlProperty(localName = "dispatchTime")
   private DispatchTime dispatchTime;
   
   /**
    * All the Load - Deadload - SLDG on DLS - (C - 200 times)
    */
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "loadInfo")
   private List<LoadInfo> loadInfo;
   
}
