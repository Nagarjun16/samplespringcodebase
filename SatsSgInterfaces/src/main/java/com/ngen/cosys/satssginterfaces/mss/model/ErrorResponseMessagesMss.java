package com.ngen.cosys.satssginterfaces.mss.model;

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
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId", "terminal", "loggedInUser" })
public class ErrorResponseMessagesMss extends BaseBO {

   /**
    * default serial version id
    */
   private static final long serialVersionUID = 1L;
   @JsonProperty(value = "ORIG_MSG_REF_NUM") 
   private String originalMessageReferenceNumber;
   @JsonProperty(value = "ORIG_TRA_REF_NUM") 
   private String originalTransactionSequenceNumber;
   @JsonProperty(value = "ORIG_MSG_TYPE") 
   private String originalMessageType;
   @JsonProperty(value = "ERR_TYPE") 
   private String errorType;
   @JsonProperty(value = "ERR_RSON") 
   private String errorReason;
   @JsonProperty(value = "ERR_DESC") 
   private String errorDescription;
   @JsonProperty(value = "ERR_FLD") 
   private String errorField;
}
