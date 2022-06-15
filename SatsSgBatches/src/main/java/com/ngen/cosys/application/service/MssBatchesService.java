package com.ngen.cosys.application.service;

import java.util.List;

import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.AgentListMessageModel;
import com.ngen.cosys.model.CloseTransitMessagesModel;
import com.ngen.cosys.model.MssMessageParentModel;
import com.ngen.cosys.model.MssModelOperativeFlightData;
import com.ngen.cosys.model.XRayRulesMessagesModel;

public interface MssBatchesService {

   MssMessageParentModel operativeFlightData(String messageName) throws CustomException;

   MssMessageParentModel mappingTableSummaryMessages(String messageName) throws CustomException;

   MssMessageParentModel mappingTableDetailModel(String messageName) throws CustomException;

   MssMessageParentModel embargoRulesMessages(String messageName) throws CustomException;

   MssMessageParentModel enroutementRuleMessages(String messageName) throws CustomException;

   MssMessageParentModel outhouseTransferRulesMessages(String messageName) throws CustomException;

   MssMessageParentModel xrayRuleMessages(String messageName) throws CustomException;

   MssMessageParentModel uldBuildUPRulesMessages(String messageName) throws CustomException;

   MssMessageParentModel agentListMessages(String messageName) throws CustomException;

   MssMessageParentModel closeTransitRuleMessages(String messageName) throws CustomException;

   void logOutgoingMessage(OutgoingMessageLog outgoingMessage);
}