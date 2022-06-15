package com.ngen.cosys.ics.model;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalTimeSerializer;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@ApiModel
public class FetchICSLocationResponseModel {

   @JsonProperty(value = "status")
   private String status;

   @JsonProperty(value = "containerId")
   private String containerId;

   @JsonProperty(value = "state")
   private String state;

   @JsonProperty(value = "location")
   private String location;

   @JsonProperty(value = "exitDate")
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate exitDate;

   @JsonProperty(value = "exitTime")
   @JsonSerialize(using = LocalTimeSerializer.class)
   private Long exitTime;

}