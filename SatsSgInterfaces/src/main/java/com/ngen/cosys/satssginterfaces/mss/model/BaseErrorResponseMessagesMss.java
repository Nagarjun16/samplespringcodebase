package com.ngen.cosys.satssginterfaces.mss.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@ApiModel
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId", "terminal", "loggedInUser" })
public class BaseErrorResponseMessagesMss {

   /**
    * system generated serial version id
    */
   private static final long serialVersionUID = 1L;

   @JsonProperty(value = "MSG_TYPE") 
   private String msgType;
 
   @JsonProperty(value = "MSG_SND_SYS") 
   private String msgSendingndSystem;
 
   @JsonProperty(value = "MSG_RCP_SYS")
   private String msgRecipientSystem;
 
   @JsonProperty(value = "DAT_MSG_SND")
   private String dataMsgSend;
   
   @JsonProperty(value = "MSG_REF_NUM") 
   private String msgReferenceNum;
 
   @JsonProperty(value = "NUM_DATA_ELMNT") 
   private String numDataElmnt;
   
   @JsonProperty(value = "DATA") 
   @JacksonXmlElementWrapper(useWrapping = false)
   ErrorResponseMessagesMss errorResponseMessagesMss;
}
