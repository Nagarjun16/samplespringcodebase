package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;
import java.time.LocalDate;

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
public class ResponseErrorMessagesHeaderMss extends BaseBO {

   /**
    * default serial version id
    */
   private static final long serialVersionUID = 1L;
   @JsonProperty(value = "MsgType") 
   private String messageType;
   @JsonProperty(value = "MsgSndSys") 
   private String messagesendSystem;
   @JsonProperty(value = "MsgRcpSys") 
   private String messageReceiveSystem;
   @JsonProperty(value = "MsgRefNum") 
   private BigInteger messageReferenceNumber;
   @JsonProperty(value = "DatMsgSnd") 
   private LocalDate dateMessageSend;
   @JsonProperty(value = "NumDataElmnt") 
   private BigInteger numberDataElement;
}
