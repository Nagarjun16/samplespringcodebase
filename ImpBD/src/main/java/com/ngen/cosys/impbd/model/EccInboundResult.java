package com.ngen.cosys.impbd.model;
/**
 * 
 * ChangeOfCustomerCode
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 Jan, 2018 NIIT -
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
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
 * This is the Base class for Change of Customer Code.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */

@ApiModel
@Component
@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
@NgenAudit(eventName = NgenAuditEventType.ECC_SHIPMNT, repository = NgenAuditEventRepository.WORKING_SHIFT, entityFieldName = "Working Shift", entityType = NgenAuditEntityType.WORKING_SHIFT)
public class EccInboundResult extends BaseBO {

   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   /**
    * Working Shift Time
    */
	@NgenAuditField(fieldName = "Working Shift")
   private String workingShift;

   /**
    * Working Shift Date.
    */
   private LocalDate date;
   /**
    * Staff Name who created the planing worksheet.
    */

   private String ID;

   private List<String> userID;

   private String user;
   /**
    * Checker assigned to the shift.
    */
   private List<String> ic;
   /**
    * EO assigned to the shift.
    */

   private List<String> teamName;
   private List<String> eoSummary;
   /**
    * User in system.
    */

   private List<AuthorizeTo> authorizeTo;
   
   @Valid
   @NgenCosysAppAnnotation 
   @NgenAuditField(fieldName = "Flight List")
   private List<ShipmentList> shipmentList;

   private LocalTime startsAt;

   private LocalTime endsAt;

   private int worksheetID;

   private int worksheetShipmentID;

   private int comTeamId;
   private int shipmentId;
   private boolean errorFlag;
   private String plannedBy;
   private String flightHandledBy;
}
