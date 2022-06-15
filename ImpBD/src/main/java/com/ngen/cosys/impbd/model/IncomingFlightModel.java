package com.ngen.cosys.impbd.model;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IncomingFlightModel extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   private long flightId;
   private String flight;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime flightDate;
   private String bay;
   private String flightType;
   private String status;
   private String boardPoint;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime sta;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime std;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime eta;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime ata;
   private String aircraft;
   private String registration;
   private String throughTransit;
   private String shortTransit;
   private String rampcheck;
   private String documentVerification;
   private String breakdown;
   private String fdlSent;
   private String ffmStatus;
   
   //Portal supporting variables
   private String staWithDate;
   private String etaWithDate;
   private String ataWithDate;
   private String customsImportFlightNumber;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime customsImportFlightDate;

	//JV01
   private String warehouseLevel;
   private String buBdOffice;
   private String gate;
   private String staffIDAndDate;
   private String remark;
   private String rho;
   private String etdDiff;
   private String arrDepStatus;
}
