package com.ngen.cosys.ics.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TimeRange {

   @JacksonXmlProperty(localName = "From")
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime fromDate;

   @JacksonXmlProperty(localName = "To")
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime toDate;

}