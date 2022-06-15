/**
 * FlightSchedulePeriod.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 28 July, 2017 NIIT -
 */

package com.ngen.cosys.flight.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This model is used to fetch Record from the configuration
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@XmlRootElement
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//@NgenAudit(eventName = NgenAuditEventType.FLIGHT_SCHEDULE_CREATION_UPDATION, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight", entityType = NgenAuditEntityType.FLIGHT)

public class FlightSchedulePeriod extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   private long flightSchedulePeriodID;

   @NotBlank(message = "g.mandatory")
   @Pattern(regexp = "^[0-9A-Za-z]*$", message = "flight.invalid")
   //@NgenAuditField(fieldName = "Carrier")
   private String flightCarrierCode;

   @NotEmpty(message = "g.mandatory")
   @Pattern(regexp = "^(([0-9]{4,4}[^0-9]{0,1})|([0-9]{3,4}))$", message = "flight.invalid")
   //@NgenAuditField(fieldName = "FlightNumber")
   private String flightNumber;

   @NotNull(message = "g.mandatory")
   //@JsonSerialize(using = LocalDateSerializer.class)
 //  @NgenAuditField(fieldName = "From Date")
   private LocalDate dateFrom;
   @NotNull(message = "g.mandatory")
   //@JsonSerialize(using = LocalDateSerializer.class)
   //@NgenAuditField(fieldName = "To Date")
   private LocalDate dateTo;
 //  @NgenAuditField(fieldName = "FlightName")
   private String flightName;
 //  @NgenAuditField(fieldName = "Flight")
   private String flight;
 //  @NgenAuditField(fieldName = "Assisted")
   private boolean assisted;
  // @NgenAuditField(fieldName = "DefaultGroundHandler")
   private String defaultGroundHandler;
   //@NgenAuditField(fieldName = "Apron")
   private boolean apron;
  // @NgenAuditField(fieldName = "GroundHandler")
   private String groundHandler;
   private boolean currentSchedule;
   //@NgenAuditField(fieldName = "Schedule FlightList")
   private List<SchFlight> schFlightList;
   
   private LocalDateTime fromDate = LocalDateTime.now();
   
   private LocalDateTime toDate = LocalDateTime.now().plusDays(30);
   
  // boolean copyFlag;
   /*
    * private LocalDate copyFromDate; private LocalDate copyToDate;
    */
}