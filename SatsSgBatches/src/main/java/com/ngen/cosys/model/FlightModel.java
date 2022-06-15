package com.ngen.cosys.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement
@ApiModel
@NoArgsConstructor
@Validated
public class FlightModel extends BaseBO {
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private BigInteger customsFlightId;
   private BigInteger flightId;
   private LocalDate flightDate;
   private String flightKey;
   private String flightBoardPoint;
   private String flightOffPoint;
   private String flightType;
   private String importExportIndicator;
   @JsonSerialize(using=LocalDateSerializer.class)
   private LocalDate  firstTimeFlightCompletedAt;
}
