package com.ngen.cosys.flight.model;

import java.time.LocalDate;

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
public class ImportFlightevents extends BaseBO {

   private static final long serialVersionUID = 1L;

   private Long flightId;

   private Long impFlightEventsId;

   private String firstULDTowedBy;

   private LocalDate firstULDTowedAt;

   private String lastULDTowedBy;

   private LocalDate lastULDTowedAt;

   private String inboundULDListFinalizedBy;

   private LocalDate inboundULDListFinalizedAt;

   private LocalDate firstULDCheckedInAt;

   private String firstULDCheckedInBy;

   private LocalDate firstTimeRampCheckInCompletedAt;

   private String firstTimeRampCheckInCompletedBy;

   private LocalDate rampCheckInCompletedAt;

   private String rampCheckInCompletedBy;

   private LocalDate firstTimeDocumentVerificationCompletedAt;

   private String firstTimeDocumentVerificationCompletedBy;

   private LocalDate documentVerificationCompletedAt;

   private String documentVerificationCompletedBy;

   private LocalDate firstTimeBreakDownCompletedAt;

   private String firstTimeBreakDownCompletedBy;

   private LocalDate breakDownCompletedAt;

   private String breakDownCompletedBy;

   private LocalDate inwardServiceReportFinalizedAt;

   private String inwardServiceReportFinalizedBy;

   private LocalDate firstTimeFlightCompletedAt;

   private String firstTimeFlightCompletedBy;

   private LocalDate flightCompletedAt;

   private String flightCompletedBy;

   private String firstTimeFlightClosedBy;

   private LocalDate firstTimeFightClosedAt;

   private String flightClosedBy;

   private LocalDate fightClosedAt;

   private LocalDate throughTransitWorkingListFinalizedAt;

   private String throughTransitWorkingListFinalizedBy;

   private String flightDiscrepncyListSentBy;

   private LocalDate flightDiscrepncyListSentAt;
}