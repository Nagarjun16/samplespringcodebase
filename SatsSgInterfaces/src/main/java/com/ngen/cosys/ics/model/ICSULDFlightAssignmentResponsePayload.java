package com.ngen.cosys.ics.model;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@ApiModel
@ToString
@Getter
@Setter
@NoArgsConstructor

public class ICSULDFlightAssignmentResponsePayload {
   private String containerId;
   private String associationAction;
   private String outgoingFlightCarrier;
   private String outgoingFlightNumber;
   private LocalDate outgoingFlightDate;
   private Long stdTime;
   private String xpsFlag;
   private String offPoint;
}