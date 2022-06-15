/**
 * {@link IVRSAWBRequest}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.service.ivrs.model;

import java.math.BigInteger;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * IVRS AWB Request
 * 
 * @author NIIT Technologies Ltd
 *
 */
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = { "shipmentDate", "messageId", "loggedInUser", "userType", "createdBy", "createdOn", //
      "modifiedBy", "modifiedOn", "versionDateTime", "flagUpdate", "flagDelete", "flagInsert", "flagSaved", //
      "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId", "terminal", "sectorId" })
@JsonPropertyOrder(value = { "messageSequenceNo", "requestDateTime", "awbPrefix", "awbSuffix" })
public class IVRSAWBRequest extends IVRSBaseBO {
   
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   //
   @JsonProperty(value = "awbPfx")
   private String awbPrefix;
   @JsonProperty(value = "awbNum")
   private String awbSuffix;
   //
   private BigInteger shipmentId;
   private LocalDate shipmentDate;
   
}
