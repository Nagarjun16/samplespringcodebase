package com.ngen.cosys.model;


import java.math.BigInteger;
import java.util.List;

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
@JsonIgnoreProperties(value = { "flightCancelFlag","flightId","createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId", "terminal", "loggedInUser", "userType", "sectorId" }) 
public class MssModelOperativeFlightData extends BaseBO {
  
   /**
    * system generated serial version id
    */
   private static final long serialVersionUID = 1L;
   /**
   *
   * This field contains Transaction Sequence Number
   */
   @JsonProperty(value = "TRA_REF_NUM") 
   private String transactionSequenceNumber;
   /**
   *
   * This field contains Action to be performed in messages
   */
   @JsonProperty(value = "ACTION")
   private String action;
   /**
   *
   * This field contains Airline Code with format 'mm(m)'
   */
   @JsonProperty(value = "FLT_CAR") 
   private String airlineCode;
   /**
   *
   * This field contains Flight Number with format 'nnnn(a)'. It will be a zero padded number
   */
   @JsonProperty(value = "FLT_NUM") 
   private String flightNumber;
   /**
   *
   * This field contains Scheduled Flight Date with format 'YYYYMMDD'
   */
   @JsonProperty(value = "SCH_FLT_DATE") 
   private String scheduleDate;
   /**
   *
   * This field contains Scheduled Flight Time with format 'HHMISS'
   */
   @JsonProperty(value = "SCH_FLT_TIME") 
   private String scheduleTime;
   /**
   *
   * This field contains Inbound Outbound Flight  with format 'aa(a)' having value 'IN','OUT'
   */
   @JsonProperty(value = "INB_OUB") 
   private String inboudOutboundFlight;
   /**
   *
   * This field contains Flight Origin Date with format 'YYYYMMDD'
   */
   @JsonProperty(value = "ORI_FLT_DATE") 
   private String flightOriginDate;
   /**
   *
   * This field contains Flight Origin Time with format 'HHMISS'
   */
   @JsonProperty(value = "ORI_FLT_TIME") 
   private String flightOriginTime;
   /**
   *
   * This field contains Type of Flight  format 'a' with value like cargo and passenger
   */
   @JsonProperty(value = "FLT_TYP") 
   private String flightType;
   /**
   *
   * This field contains Flight Remarks
   */
   @JsonProperty(value = "FLT_RMKS") 
   private String flightRemarks;
   /**
   *
   * This field contains flightid
   */
   private BigInteger flightId;
   private String flightCancelFlag;
   /**
   *
   * This field contains list of Flight legs
   */
   @JsonProperty(value = "LEG_LIST")
   private List<FlightLegsMssModel> legsList;
}