/**
 * 
 * HandoverInboundTrolly.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason v0.3 23 January, 2018 NIIT -
 */
package com.ngen.cosys.impbd.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;

import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.model.FlightModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This model class is a takes care of services call and maps to the DB table
 * 
 * @author NIIT Technologies Ltd
 *
 */
@XmlRootElement
@ApiModel
@ToString
@Getter
@Setter
@NoArgsConstructor
public class HandoverInboundTrolly extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   private BigInteger impHandOverId;
   private String tractorNumber;
   private String handedOverBy;
   private String handedOverArea;
   private LocalDateTime startedAt;
   private LocalDateTime completedAt;
   private int countofTrolleys = 0;
   private BigInteger flightId;
   private String flightNumber;
   private LocalDate flightDate;
   private int tripId;
   private FlightModel flight;
   private BigInteger ucmUldDetailsId;
   private BigInteger ucmFlightDetailsId;
   private String destination;
   private String contentCode;
   @Valid
   private List<HandoverInboundContainerTrolly> handoverInboundContainerTrolly;
   @Valid
   private List<HandoverRampCheckInModel> handoverRampCheckIn;
   
}