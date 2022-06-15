package com.ngen.cosys.model;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId", "terminal",
      "loggedInUser" , "userType", "sectorId" })
public class XRayRulesMessagesModel extends EmbargoRulesMessageModel {
   /**
    * default serial version id
    */
   private static final long serialVersionUID = 1L;
   private BigInteger xrayId;
   @JsonProperty(value = "NXT_OFF_PNT") 
   private String nextOffPoint;

}
