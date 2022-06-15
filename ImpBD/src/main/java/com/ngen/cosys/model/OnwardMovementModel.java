package com.ngen.cosys.model;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor
public class OnwardMovementModel extends BaseBO {

   private static final long serialVersionUID = 1L;

   private String airportCityCode;

   private String carrierCode;

   private String flightNumber;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime departureDate;

}