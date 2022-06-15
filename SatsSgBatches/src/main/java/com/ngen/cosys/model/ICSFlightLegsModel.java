package com.ngen.cosys.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId", "terminal", "loggedInUser" }) 
public class ICSFlightLegsModel extends BaseBO {
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
   @JsonProperty(value = "FLT_LEG_ORDER") 
   private BigInteger flightLegOrder;
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
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime scheduledDate;
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
   private LocalDate estimatedDate;
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
   private LocalDate actualDate;
   /**
   *
   * This field contains Actual Time with format 'HHMISS'
   */
   @JsonProperty(value = "ACT_FLT_TIME") 
   private String actulaTime;
   /**
   *
   * This field contains Flight Status with format 'aaa' will having status like 'COMP','MAN','DEP'
   */
   @JsonProperty(value = "FLT_STS") 
   private String flightStatus;
   
   private BigInteger flightId;
}
