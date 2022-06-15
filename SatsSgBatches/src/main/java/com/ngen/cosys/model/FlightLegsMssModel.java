package com.ngen.cosys.model;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = { "flightId","createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId", "terminal", "loggedInUser" }) 
public class FlightLegsMssModel extends BaseBO {
   /**
    * system generated serial version id
    */
   private static final long serialVersionUID = 1L;
   /**
    *
    * This field contains Board Point with format 'aaa'
    */
   @JsonProperty(value = "BRD") 
   private String boardPoint;
   /**
   *
   * This field contains Off Point with format 'aaa'
   */
   @JsonProperty(value = "OFF") 
   private String offPoint;
   /**
   *
   * This field contains Flight Leg Order with format 'nn'
   */
   @JsonProperty(value = "FLT_LEG_ODR") 
   private String flightLegOrder;
   /**
   *
   * This field contains Aircraft Types with format 'aaa'
   */
   @JsonProperty(value = "AIR_TYP") 
   private String aircraftTypes;
   /**
   *
   * This field contains Scheduled Date with format 'YYYYMMDD'
   */
   @JsonProperty(value = "SCH_FLT_DATE")
   private String scheduledDate;
   /**
   *
   * This field contains Scheduled Time with format 'HHMISS'
   */
   @JsonProperty(value = "SCH_FLT_TIME") 
   private String scheduledTime;
   /**
   *
   * This field contains Estimated Date with format 'YYYYMMDD'
   */
   @JsonProperty(value = "EST_FLT_DATE") 
   private String estimatedDate;
   /**
   *
   * This field contains Estimated Time with format 'HHMISS'
   */
   @JsonProperty(value = "EST_FLT_TIME") 
   private String estimatedTime;
   /**
   *
   * This field contains Actual Date with format 'YYYYMMDD'
   */
   @JsonProperty(value = "ACT_FLT_DATE") 
   private String actualDate;
   /**
   *
   * This field contains Actual Time with format 'HHMISS'
   */
   @JsonProperty(value = "ACT_FLT_TIME") 
   private String actualTime;
   /**
   *
   * This field contains Flight Status with format 'aaa' will having status like 'COM','MAN','DEP'
   * COM - Complete
   * MAN - Manifested
   * DEP - Departed
   */
   //TODO - Need to implement population logic by joining with Exp_FlightEvents and Imp_FlightEvents.
   @JsonProperty(value = "FLT_STS") 
   private String flightStatus = "";
   
   private BigInteger flightId;
}