package com.ngen.cosys.ics.model;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@ApiModel
public class Info {

   @JacksonXmlProperty(localName = "Time")
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime time;

   @JacksonXmlProperty(localName = "Temperature")
   private String temperature;

   @JacksonXmlProperty(localName = "Humidity")
   private String humidity;

   @JacksonXmlProperty(localName = "Stage")
   private String stage;
}