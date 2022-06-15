package com.ngen.cosys.ics.model;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookingInfo {
   @JacksonXmlProperty(localName = "Flight")
   private String flight;

   @JacksonXmlProperty(localName = "FltDate")
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate flightDate;

   @JacksonXmlProperty(localName = "Segment")
   private String segment;
}