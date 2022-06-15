package com.ngen.cosys.satssg.interfaces.singpost.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ngen.cosys.JsonSerializer.InterfaceLocalDateFormatSerializer;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
@JacksonXmlRootElement(localName = "ULDDataResponse")
@JsonIgnoreProperties(value = {"autoWeighBupHeaderId"})
public class FetchULDSuccessResponseModel {

   @JacksonXmlProperty(localName = "status")
   private String status;

   @JsonProperty("containerId")
   private String containerId;

   @JacksonXmlProperty(localName = "outgoingFlightCarrier")
   private String outgoingFlightCarrier;

   @JacksonXmlProperty(localName = "outgoingFlightNumber")
   private String outgoingFlightNumber;

   @JsonSerialize(using = InterfaceLocalDateFormatSerializer.class)
   @JacksonXmlProperty(localName = "outgoingFlightDate")
   private LocalDate outgoingFlightDate;

   //@JsonSerialize(using = InterfaceLocalTimeWithoutSecondSerializer.class)
   @JacksonXmlProperty(localName = "STDTime")
   private String stdTime;

   @JacksonXmlProperty(localName = "XPSFlag")
   private String xpsFlag;

   @JacksonXmlProperty(localName = "offPoint")
   private String offPoint;

   @JacksonXmlProperty(localName = "uldNetWeight")
   private float uldNetWeight;

   @JacksonXmlProperty(localName = "uldTareWeight")
   private float uldTareWeight;

   @JacksonXmlProperty(localName = "uldGrossWeight")
   private float uldGrossWeight;

   @JacksonXmlProperty(localName = "contentCode")
   private String contentCode;
   
   @JacksonXmlProperty(localName = "nogRemarks")
   private String nogRemarks;

   @JacksonXmlElementWrapper(localName = "dgClassList")
   @JacksonXmlProperty(localName = "Class")
   private List<ULDAutoweightList> dgClassList;

   @JacksonXmlElementWrapper(localName = "dgSubClassList")
   @JacksonXmlProperty(localName = "subClass")
   private List<ULDAutoweightSubList> dgSubClassList;
   
   private BigInteger autoWeighBupHeaderId;
}
