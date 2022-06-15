/**
 * {@link IVRSResponse}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.service.ivrs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * IVRS call Response
 * 
 * @author NIIT Technologies Ltd
 */
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = { "messageId", "loggedInUser", "userType", "createdBy", "createdOn", "modifiedBy", //
      "modifiedOn", "versionDateTime", "flagUpdate", "flagDelete", "flagInsert", "flagSaved", "localDateFormat", //
      "locale", "messageList", "flagCRUD", "tenantId", "terminal", "sectorId" })
@JsonPropertyOrder(value = { "messageSequenceNo", "requestDateTime", "status", "ackFlg", "errorCode",
      "errorDescription" })
public class IVRSResponse extends IVRSBaseBO {

   /**
    * Default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   //
   @JsonProperty(value = "status")
   private String status;
   @JsonProperty(value = "ackFlg")
   private String ackFlg;
   @JsonProperty(value = "errorCode")
   private String errorCode;
   @JsonProperty(value = "errorDesc")
   private String errorDescription;
   
}
