package com.ngen.cosys.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
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
@JsonIgnoreProperties(value = { "uldByuildUpRuleList", "outhouseRuleList", "enroutenmentRuleList", "embargoRulesList", "mappingTableSummaryList", "mappingTableDetailList", "deleteFlag", "deletedBy", "deletedOn", "createdBy", "createdOn",
      "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete", "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId", "terminal", "loggedInUser" })
public class ICSMessageParentModel<T> extends BaseBO {
   /**
    * default serial version id
    */
   private static final long serialVersionUID = 1L;
   @JsonProperty(value = "MSG_TYPE")
   private String messageType;
   @JsonProperty(value = "MSG_REF_NUM")
   private BigInteger messageRefNumber;
   @JsonProperty(value = "MSG_SND_SYS")
   private String messageSendSyatem;
   @JsonProperty(value = "MSG_RCP_SYS")
   private String messageReceipentSytem;
   @JsonProperty(value = "TRA_REF_NUM")
   private Integer numberDataElement;
   @JsonProperty(value = "DAT_MSG_SND")

   private LocalDateTime dateMessageSend = LocalDateTime.now();
   @JsonProperty(value = "DATA")
   private T data;
   @JsonProperty(value = "DATA")
   private List<ICSMappingTableSummaryModel> mappingTableSummaryList;
   @JsonProperty(value = "DATA")
   private List<ICSMappingTableDetailModel> mappingTableDetailList;
   @JsonProperty(value = "DATA")
   private List<ICSEmbargoRulesMessageModel> embargoRulesList;
   @JsonProperty(value = "DATA")
   private List<ICSEnroutementRulesMessagesModel> enroutenmentRuleList;
   @JsonProperty(value = "DATA")
   private List<ICSOuthouseTransferRulesMessagesModel> outhouseRuleList;
   @JsonProperty(value = "DATA")
   private List<ICSULDBuildupRulesModel> uldByuildUpRuleList;
   @JsonProperty(value = "DATA")
   private List<ICSAgentListMessageModel> agentList;
   @JsonProperty(value = "DATA")
   private List<ICSCloseTransitMessagesModel> closeTransitList;
}