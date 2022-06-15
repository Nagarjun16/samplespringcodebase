package com.ngen.cosys.application.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
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
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

@Repository
public class MssDAOImpl extends BaseDAO implements MssDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSession;

   @Override
   public List<MssModelOperativeFlightData> operativeFlightData() throws CustomException {
	   return super.fetchList("getFlightData",MultiTenantUtility.getAirportCityMap(""), sqlSession);
   }

   @Override
   public List<MappingTableSummaryModel> mappingTableSummaryMessages() throws CustomException {
      return super.fetchList("getMappingTableSummaryData", null, sqlSession);
   }
   
   @Override
   public List<MappingTableSummaryModel> deletedMappingTableSummaryMessages() throws CustomException {
      return super.fetchList("getDeletedMappingTableSummaryData", null, sqlSession);
   }

   @Override
   public List<MappingTableDetailModel> mappingTableDetailModel() throws CustomException {
	   return super.fetchList("getMappingTableDetailData", MultiTenantUtility.getAirportCityMap(""), sqlSession);
   }
   
   @Override
   public List<MappingTableDetailModel> deletedMappingTableModel() throws CustomException {
	   
	   return super.fetchList("getDeletedMappingTableDetailData",MultiTenantUtility.getAirportCityMap(""), sqlSession);
   }

   @Override
   public List<EmbargoRulesMessageModel> embargoRulesMessages() throws CustomException {
      return super.fetchList("getEmbargoDeatils", null, sqlSession);
   }

   @Override
   public List<EnroutementRulesMessagesModel> enroutementRuleMessages() throws CustomException {
	   return super.fetchList("getEnroutementRules", MultiTenantUtility.getAirportCityMap(""), sqlSession);
   }
   
   @Override
   public List<EnroutementRulesMessagesModel> deletedEnroutementRuleMessages() throws CustomException {
      return super.fetchList("getDeletedEnroutementRules", null, sqlSession);
   }

   @Override
   public List<OuthouseTransferRulesMessagesModel> outhouseTransferRulesMessages() throws CustomException {
      return super.fetchList("getOuthouseMessages", null, sqlSession);
   }
   
   @Override
   public List<OuthouseTransferRulesMessagesModel> deletedOuthouseTransferRulesMessages() throws CustomException {
      return super.fetchList("getDeletedOuthouseMessages", null, sqlSession);
   }
   
   @Override
   public List<XRayRulesMessagesModel> xrayRulesMessages() throws CustomException {
      return super.fetchList("getXrayDetails", null, sqlSession);
   }
   
   @Override
   public List<XRayRulesMessagesModel> deletedXrayRulesMessages() throws CustomException {
      return super.fetchList("getDeletedXrayDetails", null, sqlSession);
   }
   
   @Override
   public List<ULDBuildupRulesModel> uldBuildUPRulesMessages() throws CustomException {
      return super.fetchList("getUldBuildUpDetails", null, sqlSession);
   }
   
   @Override
   public List<ULDBuildupRulesModel> deletedUldBuildUPRulesMessages() throws CustomException {
      return super.fetchList("getDeletedBuildUpDetails", null, sqlSession);
   }
   
   @Override
   public List<AgentListMessageModel> agentListMessges() throws CustomException {
      return super.fetchList("getAgentListDetails", null, sqlSession);
   }
   
   @Override
   public List<AgentListMessageModel> deletedAgentListMessges() throws CustomException {
      return super.fetchList("getDeletedAgentListDetails", null, sqlSession);
   }

   @Override
   public List<CloseTransitMessagesModel> closedTransitRulesMessages() throws CustomException {
      return super.fetchList("getCloseTransit", null, sqlSession);
   }

   @Override
   public List<CloseTransitMessagesModel> deletedClosedTransitRulesMessages() throws CustomException {
      return super.fetchList("getDeletedCloseTransit", null, sqlSession);
   }

   

}
