package com.ngen.cosys.application.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.ngen.cosys.application.dao.MssDAO;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.AgentListMessageModel;
import com.ngen.cosys.model.CloseTransitMessagesModel;
import com.ngen.cosys.model.EmbargoRulesMessageModel;
import com.ngen.cosys.model.EnroutementRulesMessagesModel;
import com.ngen.cosys.model.MappingTableDetailModel;
import com.ngen.cosys.model.MappingTableSummaryModel;
import com.ngen.cosys.model.MssMessageParentModel;
import com.ngen.cosys.model.MssModelOperativeFlightData;
import com.ngen.cosys.model.OuthouseTransferRulesMessagesModel;
import com.ngen.cosys.model.ULDBuildupRulesModel;
import com.ngen.cosys.model.XRayRulesMessagesModel;

@Service
public class MssbatchesServiceImpl implements MssBatchesService {
   
   public enum Action {
      ADD("ADD"), UPD("UPD"), DEL("DEL"), ACTIVE("A"), DELETED("D");

      private final String type;

      private Action(String value) {
         this.type = value;
      }

      /*
       * (non-Javadoc)
       * 
       * @see java.lang.Enum#toString()
       */
      @Override
      public String toString() {
         return String.valueOf(this.type);
      }

      /**
       * Returns the ENUM for the specified String.
       * 
       * @param value
       * @return
       */
      @JsonCreator
      public static Action fromValue(String value) {
         for (Action eType : values()) {
            if (eType.type.equalsIgnoreCase(value)) {
               return eType;
            }
         }
         throw new IllegalArgumentException(
               "Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
      }
   }
   
   private String senderName = "MSS";
   
   private String receiverName = "RFDT";
   
   @Autowired 
   private ApplicationLoggerService applicationLoggerService;

   @Autowired
   private MssDAO dao;

   private Random das = new Random();
   
   @Override
   public MssMessageParentModel<List<MssModelOperativeFlightData>> operativeFlightData(String messageName) throws CustomException {
      List<MssModelOperativeFlightData> data = dao.operativeFlightData();
      List<MssModelOperativeFlightData> listCreateData = data.stream()
            .filter(obj -> (null == obj.getModifiedOn() || obj.getCreatedOn().equals(obj.getModifiedOn()))
                  && Action.ACTIVE.toString().equalsIgnoreCase(obj.getFlightCancelFlag()))
            .collect(Collectors.toList());
      for (MssModelOperativeFlightData value : listCreateData) {
         value.setAction(Action.ADD.toString());
      }
      List<MssModelOperativeFlightData> listUpdateData = data.stream().filter(obj -> null != obj.getModifiedOn()
            && obj.getModifiedOn().isAfter(obj.getCreatedOn()) && Action.ACTIVE.toString().equalsIgnoreCase(obj.getFlightCancelFlag()))
            .collect(Collectors.toList());
      for (MssModelOperativeFlightData value : listUpdateData) {
         value.setAction(Action.UPD.toString());
      }
      List<MssModelOperativeFlightData> listdeleteData = data.stream()
            .filter(obj -> Action.DELETED.toString().equalsIgnoreCase(obj.getFlightCancelFlag())).collect(Collectors.toList());
      for (MssModelOperativeFlightData value : listdeleteData) {
         value.setAction(Action.DEL.toString());
      }
      List<MssModelOperativeFlightData> combinedList = new ArrayList<>();
      combinedList.addAll(listCreateData);
      combinedList.addAll(listUpdateData);
      combinedList.addAll(listdeleteData);
      // value.setTransactionSequenceNumber(Integer.toString(UUID.randomUUID().variant()));
      MssMessageParentModel<List<MssModelOperativeFlightData>> parentData = new MssMessageParentModel<>();
      parentData.setMessageType(messageName);
      parentData.setMessageRefNumber(BigInteger.valueOf(das.nextInt(9999999)));
      parentData.setMessageSendSyatem(this.receiverName);
      parentData.setMessageReceipentSytem(this.senderName);
      parentData.setNumberDataElement(combinedList.size());
      parentData.setData(combinedList);
      return parentData;
   }

   @Override
   public MssMessageParentModel<List<MappingTableSummaryModel>> mappingTableSummaryMessages(String messageName) throws CustomException {
      List<MappingTableSummaryModel> mappingparentDataList = dao.mappingTableSummaryMessages();
      List<MappingTableSummaryModel> listCreateData = mappingparentDataList.stream()
            .filter(obj -> (null == obj.getModifiedOn() || obj.getCreatedOn().equals(obj.getModifiedOn()))
                  && !obj.getDeleteFlag())
            .collect(Collectors.toList());
      for (MappingTableSummaryModel value : listCreateData) {
         value.setAction(Action.ADD.toString());
      }
      List<MappingTableSummaryModel> listUpdateData = mappingparentDataList.stream()
            .filter(obj -> null != obj.getModifiedOn() && obj.getModifiedOn().isAfter(obj.getCreatedOn()))
            .collect(Collectors.toList());
      for (MappingTableSummaryModel value : listUpdateData) {
         value.setAction(Action.UPD.toString());
      }
      /*
       * List<MappingTableSummaryModel> listdeleteData =
       * mappingparentDataList.stream() .filter(obj ->
       * (obj.getDeleteFlag())).collect(Collectors.toList()); for
       * (MappingTableSummaryModel value : listdeleteData) { value.setAction(Action.DEL.toString());
       * value.setTransactionSequenceNumber(Integer.toString(das.nextInt(99999999)));
       * }
       */
      List<MappingTableSummaryModel> listdeleteData = dao.deletedMappingTableSummaryMessages();
      for (MappingTableSummaryModel value : listdeleteData) {
         value.setAction(Action.DEL.toString());
      }
      List<MappingTableSummaryModel> combinedList = new ArrayList<>();
      combinedList.addAll(listCreateData);
      combinedList.addAll(listUpdateData);
      combinedList.addAll(listdeleteData);
      MssMessageParentModel<List<MappingTableSummaryModel>> parentData = new MssMessageParentModel<>();
      parentData.setMessageType(messageName);
      parentData.setMessageRefNumber(BigInteger.valueOf(das.nextInt(9999999)));
      parentData.setMessageSendSyatem(this.receiverName);
      parentData.setMessageReceipentSytem(this.senderName);
      parentData.setNumberDataElement(combinedList.size());
      parentData.setData(combinedList);
      return parentData;
   }

   @Override
   public MssMessageParentModel<List<MappingTableDetailModel>> mappingTableDetailModel(String messageName) throws CustomException {
      List<MappingTableDetailModel> mappingTableDetailList = dao.mappingTableDetailModel();
      List<MappingTableDetailModel> listCreateData = mappingTableDetailList.stream()
            .filter(obj -> (null == obj.getModifiedOn() || obj.getCreatedOn().equals(obj.getModifiedOn()))
                  && !obj.getDeleteFlag())
            .collect(Collectors.toList());
      for (MappingTableDetailModel value : listCreateData) {
         value.setAction(Action.ADD.toString());
      }
      List<MappingTableDetailModel> listUpdateData = mappingTableDetailList.stream()
            .filter(obj -> null != obj.getModifiedOn() && obj.getModifiedOn().isAfter(obj.getCreatedOn())
                  && !obj.getDeleteFlag())
            .collect(Collectors.toList());
      for (MappingTableDetailModel value : listUpdateData) {
         value.setAction(Action.UPD.toString());
      }
      /*
       * List<MappingTableDetailModel> listdeleteData =
       * mappingTableDetailList.stream() .filter(obj ->
       * (obj.getDeleteFlag())).collect(Collectors.toList()); for
       * (MappingTableDetailModel value : listdeleteData) { value.setAction(Action.DEL.toString());
       * value.setTransactionSequenceNumber(Integer.toString(das.nextInt(99999999)));
       * }
       */
      List<MappingTableDetailModel> listdeleteData = dao.deletedMappingTableModel();
      for (MappingTableDetailModel value : listdeleteData) {
         value.setAction(Action.DEL.toString());
      }
      List<MappingTableDetailModel> combinedList = new ArrayList<>();
      combinedList.addAll(listCreateData);
      combinedList.addAll(listUpdateData);
      combinedList.addAll(listdeleteData);
      MssMessageParentModel<List<MappingTableDetailModel>> parentData = new MssMessageParentModel<>();
      parentData.setMessageType(messageName);
      parentData.setMessageRefNumber(BigInteger.valueOf(das.nextInt(9999999)));
      parentData.setMessageSendSyatem(this.receiverName);
      parentData.setMessageReceipentSytem(this.senderName);
      parentData.setNumberDataElement(combinedList.size());
      parentData.setData(combinedList);
      return parentData;
   }

   @Override
   public MssMessageParentModel<List<EmbargoRulesMessageModel>> embargoRulesMessages(String messageName) throws CustomException {
      List<EmbargoRulesMessageModel> embargoRulesMessageList = dao.embargoRulesMessages();
      List<EmbargoRulesMessageModel> listCreateData = embargoRulesMessageList.stream()
            .filter(obj -> (null == obj.getModifiedOn() || obj.getCreatedOn().equals(obj.getModifiedOn()))
                  && !obj.getDeleteFlag())
            .collect(Collectors.toList());
      for (EmbargoRulesMessageModel value : listCreateData) {
         value.setAction(Action.ADD.toString());
      }
      List<EmbargoRulesMessageModel> listUpdateData = embargoRulesMessageList.stream()
            .filter(obj -> null != obj.getModifiedOn() && obj.getModifiedOn().isAfter(obj.getCreatedOn())
                  && !obj.getDeleteFlag())
            .collect(Collectors.toList());
      for (EmbargoRulesMessageModel value : listUpdateData) {
         value.setAction(Action.UPD.toString());
      }
      List<EmbargoRulesMessageModel> listdeleteData = embargoRulesMessageList.stream()
            .filter(obj -> (obj.getDeleteFlag())).collect(Collectors.toList());
      for (EmbargoRulesMessageModel value : listdeleteData) {
         value.setAction(Action.DEL.toString());
      }
      List<EmbargoRulesMessageModel> combinedList = new ArrayList<>();
      combinedList.addAll(listCreateData);
      combinedList.addAll(listUpdateData);
      combinedList.addAll(listdeleteData);
      MssMessageParentModel<List<EmbargoRulesMessageModel>> parentData = new MssMessageParentModel<>();
      parentData.setMessageType(messageName);
      parentData.setMessageRefNumber(BigInteger.valueOf(das.nextInt(9999999)));
      parentData.setMessageSendSyatem(this.receiverName);
      parentData.setMessageReceipentSytem(this.senderName);
      parentData.setNumberDataElement(combinedList.size());
      parentData.setData(combinedList);
      return parentData;
   }

   @Override
   public MssMessageParentModel<List<EnroutementRulesMessagesModel>> enroutementRuleMessages(String messageName) throws CustomException {
      List<EnroutementRulesMessagesModel> enroutementRulesDataList = dao.enroutementRuleMessages();
      List<EnroutementRulesMessagesModel> listCreateData = enroutementRulesDataList.stream()
            .filter(obj -> (null == obj.getModifiedOn() || obj.getCreatedOn().equals(obj.getModifiedOn()))
                  && !obj.getDeleteFlag())
            .collect(Collectors.toList());
      for (EnroutementRulesMessagesModel value : listCreateData) {
         value.setAction(Action.ADD.toString());
      }
      List<EnroutementRulesMessagesModel> listUpdateData = enroutementRulesDataList.stream()
            .filter(obj -> null != obj.getModifiedOn() && obj.getModifiedOn().isAfter(obj.getCreatedOn())
                  && !obj.getDeleteFlag())
            .collect(Collectors.toList());
      for (EnroutementRulesMessagesModel value : listUpdateData) {
         value.setAction(Action.UPD.toString());
      }
      /*
       * List<EnroutementRulesMessagesModel> listdeleteData =
       * enroutementRulesDataList.stream() .filter(obj ->
       * (obj.getDeleteFlag())).collect(Collectors.toList()); for
       * (EnroutementRulesMessagesModel value : listdeleteData) {
       * value.setAction(Action.DEL.toString());
       * value.setTransactionSequenceNumber(Integer.toString(das.nextInt(99999999)));
       * }
       */
      List<EnroutementRulesMessagesModel> listdeleteData = dao.deletedEnroutementRuleMessages();
      for (EnroutementRulesMessagesModel value : listdeleteData) {
         value.setAction(Action.DEL.toString());
      }
      List<EnroutementRulesMessagesModel> combinedList = new ArrayList<>();
      combinedList.addAll(listCreateData);
      combinedList.addAll(listUpdateData);
      combinedList.addAll(listdeleteData);
      MssMessageParentModel<List<EnroutementRulesMessagesModel>> parentData = new MssMessageParentModel<>();
      parentData.setMessageType(messageName);
      parentData.setMessageRefNumber(BigInteger.valueOf(das.nextInt(9999999)));
      parentData.setMessageSendSyatem(this.receiverName);
      parentData.setMessageReceipentSytem(this.senderName);
      parentData.setNumberDataElement(combinedList.size());
      parentData.setData(combinedList);
      return parentData;
   }

   @Override
   public MssMessageParentModel<List<Object>> outhouseTransferRulesMessages(String messageName) throws CustomException {
      List<OuthouseTransferRulesMessagesModel> outhouseDataList = dao.outhouseTransferRulesMessages();
      List<OuthouseTransferRulesMessagesModel> listCreateData = outhouseDataList.stream()
            .filter(obj -> (null == obj.getModifiedOn() || obj.getCreatedOn().equals(obj.getModifiedOn()))
                  && !obj.getDeleteFlag())
            .collect(Collectors.toList());
      for (OuthouseTransferRulesMessagesModel value : listCreateData) {
         value.setAction(Action.ADD.toString());
      }
      List<OuthouseTransferRulesMessagesModel> listUpdateData = outhouseDataList.stream()
            .filter(obj -> null != obj.getModifiedOn() && obj.getModifiedOn().isAfter(obj.getCreatedOn())
                  && !obj.getDeleteFlag())
            .collect(Collectors.toList());
      for (OuthouseTransferRulesMessagesModel value : listUpdateData) {
         value.setAction(Action.UPD.toString());
      }
      /*
       * List<OuthouseTransferRulesMessagesModel> listdeleteData =
       * outhouseDataList.stream() .filter(obj ->
       * (obj.getDeleteFlag())).collect(Collectors.toList()); for
       * (OuthouseTransferRulesMessagesModel value : listdeleteData) {
       * value.setAction(Action.DEL.toString());
       * value.setTransactionSequenceNumber(Integer.toString(das.nextInt(99999999)));
       * }
       */
      List<OuthouseTransferRulesMessagesModel> listdeleteData = dao.deletedOuthouseTransferRulesMessages();
      for (OuthouseTransferRulesMessagesModel value : listdeleteData) {
         value.setAction(Action.DEL.toString());
      }
      List<OuthouseTransferRulesMessagesModel> combinedList = new ArrayList<>();
      combinedList.addAll(listCreateData);
      combinedList.addAll(listUpdateData);
      combinedList.addAll(listdeleteData);
      MssMessageParentModel<List<XRayRulesMessagesModel>> xrayData = this.xrayRuleMessages(messageName);
      List<Object> listObject = new ArrayList<Object>();
      listObject.addAll(combinedList);
      listObject.addAll(xrayData.getData());
      MssMessageParentModel<List<Object>> parentData = new MssMessageParentModel<>();
      parentData.setMessageType(messageName);
      parentData.setMessageRefNumber(BigInteger.valueOf(das.nextInt(9999999)));
      parentData.setMessageSendSyatem(this.receiverName);
      parentData.setMessageReceipentSytem(this.senderName);
      parentData.setNumberDataElement(combinedList.size());
      parentData.setData(listObject);
      return parentData;
   }

   @Override
   public MssMessageParentModel<List<XRayRulesMessagesModel>> xrayRuleMessages(String messageName) throws CustomException {
      List<XRayRulesMessagesModel> xrayRuleDataList = dao.xrayRulesMessages();
      List<XRayRulesMessagesModel> listCreateData = xrayRuleDataList.stream()
            .filter(obj -> (null == obj.getModifiedOn() || obj.getCreatedOn().equals(obj.getModifiedOn()))
                  && !obj.getDeleteFlag())
            .collect(Collectors.toList());
      for (XRayRulesMessagesModel value : listCreateData) {
         value.setAction(Action.ADD.toString());
      }
      List<XRayRulesMessagesModel> listUpdateData = xrayRuleDataList.stream().filter(obj -> null != obj.getModifiedOn()
            && obj.getModifiedOn().isAfter(obj.getCreatedOn()) && !obj.getDeleteFlag()).collect(Collectors.toList());
      for (XRayRulesMessagesModel value : listUpdateData) {
         value.setAction(Action.UPD.toString());
      }
      /*
       * List<XRayRulesMessagesModel> listdeleteData =
       * xrayRuleDataList.stream().filter(obj -> (obj.getDeleteFlag()))
       * .collect(Collectors.toList()); for (XRayRulesMessagesModel value :
       * listdeleteData) { value.setAction(Action.DEL.toString());
       * value.setTransactionSequenceNumber(Integer.toString(das.nextInt(99999999)));
       * }
       */
      List<XRayRulesMessagesModel> listdeleteData = dao.deletedXrayRulesMessages();
      for (XRayRulesMessagesModel value : listdeleteData) {
         value.setAction(Action.DEL.toString());
      }
      List<XRayRulesMessagesModel> combinedList = new ArrayList<>();
      combinedList.addAll(listCreateData);
      combinedList.addAll(listUpdateData);
      combinedList.addAll(listdeleteData);
      MssMessageParentModel<List<XRayRulesMessagesModel>> parentData = new MssMessageParentModel<>();
      parentData.setMessageType(messageName);
      parentData.setMessageRefNumber(BigInteger.valueOf(das.nextInt(9999999)));
      parentData.setMessageSendSyatem(this.receiverName);
      parentData.setMessageReceipentSytem(this.senderName);
      parentData.setNumberDataElement(combinedList.size());
      parentData.setData(combinedList);
      return parentData;
   }

   @Override
   public MssMessageParentModel<List<ULDBuildupRulesModel>> uldBuildUPRulesMessages(String messageName) throws CustomException {
      List<ULDBuildupRulesModel> uldBuildUpList = dao.uldBuildUPRulesMessages();
    
      List<ULDBuildupRulesModel> listCreateData = uldBuildUpList.stream()
            .filter(obj -> (null == obj.getModifiedOn() || obj.getCreatedOn().equals(obj.getModifiedOn())))
            .collect(Collectors.toList());
      for (ULDBuildupRulesModel value : listCreateData) {
         value.setAction(Action.ADD.toString());
      }
      List<ULDBuildupRulesModel> listUpdateData = uldBuildUpList.stream().filter(obj -> null != obj.getModifiedOn()
            && obj.getModifiedOn().isAfter(obj.getCreatedOn()) && !obj.getDeleteFlag()).collect(Collectors.toList());
      for (ULDBuildupRulesModel value : listUpdateData) {
         value.setAction(Action.UPD.toString());
      }
      /*
       * List<ULDBuildupRulesModel> listdeleteData =
       * uldBuildUpList.stream().filter(obj -> (obj.getDeleteFlag()))
       * .collect(Collectors.toList()); for (ULDBuildupRulesModel value :
       * listdeleteData) { value.setAction(Action.DEL.toString());
       * value.setTransactionSequenceNumber(Integer.toString(das.nextInt(99999999)));
       * }
       */
      List<ULDBuildupRulesModel> listdeleteData = dao.deletedUldBuildUPRulesMessages();
      for (ULDBuildupRulesModel value : listdeleteData) {
         value.setAction(Action.DEL.toString());
      }
      List<ULDBuildupRulesModel> combinedList = new ArrayList<>();
      combinedList.addAll(listCreateData);
      combinedList.addAll(listUpdateData);
      combinedList.addAll(listdeleteData);
      MssMessageParentModel<List<ULDBuildupRulesModel>> parentData = new MssMessageParentModel<>();
      parentData.setMessageType(messageName);
      parentData.setMessageRefNumber(BigInteger.valueOf(das.nextInt(9999999)));
      parentData.setMessageSendSyatem(this.receiverName);
      parentData.setMessageReceipentSytem(this.senderName);
      parentData.setNumberDataElement(combinedList.size());
      parentData.setData(combinedList);
      return parentData;
   }

   @Override
   public MssMessageParentModel<List<AgentListMessageModel>> agentListMessages(String messageName) throws CustomException {
      List<AgentListMessageModel> agenListDataList = dao.agentListMessges();
      List<AgentListMessageModel> listCreateData = agenListDataList.stream()
            .filter(obj -> (null == obj.getModifiedOn() || obj.getCreatedOn().equals(obj.getModifiedOn()))
                  && !obj.getDeleteFlag())
            .collect(Collectors.toList());
      for (AgentListMessageModel value : listCreateData) {
         value.setAction(Action.ADD.toString());
      }
      List<AgentListMessageModel> listUpdateData = agenListDataList.stream().filter(obj -> null != obj.getModifiedOn()
            && obj.getModifiedOn().isAfter(obj.getCreatedOn()) && !obj.getDeleteFlag()).collect(Collectors.toList());
      for (AgentListMessageModel value : listUpdateData) {
         value.setAction(Action.UPD.toString());
      }
      List<AgentListMessageModel> listdeleteData = dao.deletedAgentListMessges();
      for (AgentListMessageModel value : listdeleteData) {
         value.setAction(Action.DEL.toString());
      }
      List<AgentListMessageModel> combinedList = new ArrayList<>();
      combinedList.addAll(listCreateData);
      combinedList.addAll(listUpdateData);
      combinedList.addAll(listdeleteData);
      MssMessageParentModel<List<AgentListMessageModel>> parentData = new MssMessageParentModel<>();
      parentData.setMessageType(messageName);
      parentData.setMessageRefNumber(BigInteger.valueOf(das.nextInt(9999999)));
      parentData.setMessageSendSyatem(this.receiverName);
      parentData.setMessageReceipentSytem(this.senderName);
      parentData.setNumberDataElement(combinedList.size());
      parentData.setData(combinedList);
      return parentData;
   }

   @Override
   public MssMessageParentModel<List<CloseTransitMessagesModel>>  closeTransitRuleMessages(String messageName) throws CustomException {
      List<CloseTransitMessagesModel> closeTransitDataList = dao.closedTransitRulesMessages();
      List<CloseTransitMessagesModel> listCreateData = closeTransitDataList.stream()
            .filter(obj -> (null == obj.getModifiedOn() || obj.getCreatedOn().equals(obj.getModifiedOn()))
                  && !obj.getDeleteFlag())
            .collect(Collectors.toList());
      for (CloseTransitMessagesModel value : listCreateData) {
         value.setAction(Action.ADD.toString());
      }
      List<CloseTransitMessagesModel> listUpdateData = closeTransitDataList.stream()
            .filter(obj -> null != obj.getModifiedOn() && obj.getModifiedOn().isAfter(obj.getCreatedOn())
                  && !obj.getDeleteFlag())
            .collect(Collectors.toList());
      for (CloseTransitMessagesModel value : listUpdateData) {
         value.setAction(Action.UPD.toString());
      }
     /* List<CloseTransitMessagesModel> listdeleteData = dao.deletedClosedTransitRulesMessages();
      for (CloseTransitMessagesModel value : listdeleteData) {
         value.setAction(Action.DEL.toString());
      }*/
      List<CloseTransitMessagesModel> combinedList = new ArrayList<>();
      combinedList.addAll(listCreateData);
      combinedList.addAll(listUpdateData);
      //combinedList.addAll(listdeleteData);
      MssMessageParentModel<List<CloseTransitMessagesModel>> parentData = new MssMessageParentModel<>();
      parentData.setMessageType(messageName);
      parentData.setMessageRefNumber(BigInteger.valueOf(das.nextInt(9999999)));
      parentData.setMessageSendSyatem(this.receiverName);
      parentData.setMessageReceipentSytem(this.senderName);
      parentData.setNumberDataElement(combinedList.size());
      parentData.setData(combinedList);
      return parentData;
   }

   public void logOutgoingMessage(OutgoingMessageLog outgoingMessage) {
      applicationLoggerService.logOutgoingMessage(outgoingMessage);
   }
}