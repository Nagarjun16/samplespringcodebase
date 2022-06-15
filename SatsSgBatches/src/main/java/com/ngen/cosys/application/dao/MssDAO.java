package com.ngen.cosys.application.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.AgentListMessageModel;
import com.ngen.cosys.model.CloseTransitMessagesModel;
import com.ngen.cosys.model.EmbargoRulesMessageModel;
import com.ngen.cosys.model.EnroutementRulesMessagesModel;
import com.ngen.cosys.model.MappingTableDetailModel;
import com.ngen.cosys.model.MappingTableSummaryModel;
import com.ngen.cosys.model.MssModelOperativeFlightData;
import com.ngen.cosys.model.OuthouseTransferRulesMessagesModel;
import com.ngen.cosys.model.ULDBuildupRulesModel;
import com.ngen.cosys.model.XRayRulesMessagesModel;

public interface MssDAO {

   List<MssModelOperativeFlightData> operativeFlightData() throws CustomException;
   List<MappingTableSummaryModel> mappingTableSummaryMessages() throws CustomException;
   List<MappingTableSummaryModel> deletedMappingTableSummaryMessages() throws CustomException;
   List<MappingTableDetailModel> mappingTableDetailModel() throws CustomException;
   List<MappingTableDetailModel> deletedMappingTableModel() throws CustomException;
   List<EmbargoRulesMessageModel> embargoRulesMessages() throws CustomException;
   List<EnroutementRulesMessagesModel> enroutementRuleMessages() throws CustomException;
   List<EnroutementRulesMessagesModel> deletedEnroutementRuleMessages() throws CustomException;
   List<OuthouseTransferRulesMessagesModel> outhouseTransferRulesMessages() throws CustomException;
   List<OuthouseTransferRulesMessagesModel> deletedOuthouseTransferRulesMessages() throws CustomException;
   List<XRayRulesMessagesModel> xrayRulesMessages() throws CustomException;
   List<XRayRulesMessagesModel> deletedXrayRulesMessages() throws CustomException;
   List<ULDBuildupRulesModel> uldBuildUPRulesMessages() throws CustomException;
   List<ULDBuildupRulesModel> deletedUldBuildUPRulesMessages() throws CustomException;
   List<AgentListMessageModel> agentListMessges() throws CustomException;
   List<AgentListMessageModel> deletedAgentListMessges() throws CustomException;
   List<CloseTransitMessagesModel> closedTransitRulesMessages() throws CustomException;  
   List<CloseTransitMessagesModel> deletedClosedTransitRulesMessages() throws CustomException;
   
}
