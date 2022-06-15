package com.ngen.cosys.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.InterfaceLocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = { "uldByuildUpRuleList","outhouseRuleList","enroutenmentRuleList","embargoRulesList","mappingTableSummaryList","mappingTableDetailList","deleteFlag","deletedBy","deletedOn","createdBy", "createdOn", "modifiedBy", "modifiedOn",  "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId", "terminal", "loggedInUser","userType", "sectorId" })
public class MssMessageParentModel<T> extends BaseBO {
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
   @JsonProperty(value = "NUM_DATA_ELMNT")
   private Integer numberDataElement;
   @JsonProperty(value = "DAT_MSG_SND")
   @JsonSerialize(using = InterfaceLocalDateTimeSerializer.class)
   private LocalDateTime dateMessageSend = LocalDateTime.now();
   @JsonProperty(value = "DATA")
   private T data;
   /*@JsonProperty(value = "DATA")
   private List<MappingTableSummaryModel> mappingTableSummaryList;
   @JsonProperty(value = "DATA")
   private List<MappingTableDetailModel> mappingTableDetailList;
   @JsonProperty(value = "DATA")
   private List<EmbargoRulesMessageModel> embargoRulesList;
   @JsonProperty(value = "DATA")
   private List<EnroutementRulesMessagesModel> enroutenmentRuleList;
   @JsonProperty(value = "DATA")
   private List<OuthouseTransferRulesMessagesModel> outhouseRuleList;
   @JsonProperty(value = "DATA")
   private List<ULDBuildupRulesModel> uldByuildUpRuleList;
   @JsonProperty(value = "DATA")
   private List<AgentListMessageModel> agentList;
   @JsonProperty(value = "DATA")
   private List<CloseTransitMessagesModel> closeTransitList;*/
}