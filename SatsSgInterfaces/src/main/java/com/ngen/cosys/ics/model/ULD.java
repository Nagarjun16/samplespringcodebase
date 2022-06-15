package com.ngen.cosys.ics.model;

import java.math.BigInteger;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.ngen.cosys.JsonSerializer.InterfaceLocalDateFormatSerializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ULD {

   @JacksonXmlProperty(localName = "containerId")
   private String containerId;

   @JacksonXmlProperty(localName = "uldCreateDate")
   @JsonSerialize(using = InterfaceLocalDateFormatSerializer.class)
   private LocalDate uldCreateDate;

   @JacksonXmlProperty(localName = "uldUpdateDate")
   @JsonSerialize(using = InterfaceLocalDateFormatSerializer.class)
   private LocalDate uldUpdateDate;

   @JacksonXmlProperty(localName = "throughServiceFlag")
   private String throughServiceFlag;

   @JacksonXmlProperty(localName = "sttFlag")
   private String sttFlag;

   @JacksonXmlProperty(localName = "perishableFlag")
   private String perishableFlag;

   @JacksonXmlProperty(localName = "valuableFlag")
   private String valuableFlag;

   @JacksonXmlProperty(localName = "uldStatus")
   private String uldStatus;

   @JacksonXmlProperty(localName = "uldContentCode")
   private String uldContentCode;

   @JacksonXmlProperty(localName = "incomingFlightCarrier")
   private String incomingFlightCarrier;

   @JacksonXmlProperty(localName = "incomingFlightNumber")
   private String incomingFlightNumber;

   @JacksonXmlProperty(localName = "incomingFlightDate")
   @JsonSerialize(using = InterfaceLocalDateFormatSerializer.class)
   private LocalDate incomingFlightDate;

   @JacksonXmlProperty(localName = "uldFlightOriginPoint")
   private String uldFlightOriginPoint;

   @JacksonXmlProperty(localName = "uldFlightOffPoint")
   private String uldFlightOffPoint;

   @JacksonXmlProperty(localName = "outgoingFlightCarrier")
   private String outgoingFlightCarrier;

   @JacksonXmlProperty(localName = "outgoingFlightNumber")
   private String outgoingFlightNumber;

   @JsonSerialize(using = InterfaceLocalDateFormatSerializer.class)
   @JacksonXmlProperty(localName = "outgoingFlightDate")
   private LocalDate outgoingFlightDate;

   @JacksonXmlProperty(localName = "pchsDestination")
   private String pchsDestination;

   @JacksonXmlProperty(localName = "manualInputFlag")
   private String manualInputFlag;

   @JacksonXmlProperty(localName = "sourceIdentifier")
   private String sourceIdentifier;

   @JacksonXmlProperty(localName = "warehouseDestination")
   private String warehouseDestination;

   @JacksonXmlProperty(localName = "uldRemark")
   private String uldRemark;

   @JacksonXmlProperty(localName = "cargoTerminal")
   private String cargoTerminal;

/*   @JacksonXmlProperty(localName = "connetingFlightId")
   private String connetingFlightId;*/

/*   @JacksonXmlProperty(localName = "flightKey")
   private String flightKey;

   @JacksonXmlProperty(localName = "flightId")
   private BigInteger flightId;
*/
}
