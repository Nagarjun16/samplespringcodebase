package com.ngen.cosys.model;

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
@JsonIgnoreProperties(value = { "deletedOn","deletedBy","deleteFlag","createdBy", "createdOn", "modifiedBy", "modifiedOn",  "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId", "terminal",
      "loggedInUser", "agentCode", "nextOffPoint", "outgoingCarrier" })
public class OuthouseTransferRulesMessagesModel extends EnroutementRulesMessagesModel {
   /**
    * default serial version id
    */
   private static final long serialVersionUID = 1L;
   @JsonProperty(value = "NON_AST_CAR") 
   private String nonAssistantCarrier;
}