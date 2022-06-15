package com.ngen.cosys.flight.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
@Scope("prototype")
public class ExportFlightevents extends BaseBO {
   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private Long flightId;

   private Long expFlightEventsId;

   private LocalTime bookingClosingTime;

   private String firstTimeBuildCompletedBy;

   private LocalDate firstTimeBuildCompletedAt;

   private String buildupCompletedBy;

   private LocalDate buildupCompletedAt;

   private String firstTimeManifestCompletedBy;

   private LocalDate firstTimeManifestCompletedAt;

   private String manifestCompletedBy;

   private LocalDate manifestCompletedAt;

   private String firstTimeDLSCompletedBy;

   private LocalDate firstTimeDLSCompletedAt;

   private String DLSCompletedBy;

   private LocalDate DLSCompletedAt;

   private String offloadCompletedBy;

   private LocalDate offloadCompletedAt;

   private String outwardServiceReportFinalizedBy;

   private LocalDate outwardServiceReportFinalizedAt;

   private String firstTimeFlightCompletedBy;

   private LocalDate firstTimeFlightCompletedAt;

   private String flightCompletedBy;

   private LocalDate flightCompletedAt;

   private String flightDepartedBy;

   private LocalDate flightDepartedOn;
}