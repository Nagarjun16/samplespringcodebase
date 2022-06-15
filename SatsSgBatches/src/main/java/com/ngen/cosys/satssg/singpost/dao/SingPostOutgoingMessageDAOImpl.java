package com.ngen.cosys.satssg.singpost.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.satssg.singpost.model.FlightTouchDown;
import com.ngen.cosys.satssg.singpost.model.MailBagRequestModel;
@Repository
public class SingPostOutgoingMessageDAOImpl extends BaseDAO implements SingPostOutgoingMessageDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSession;

   @Override
   public List<MailBagRequestModel> pushBagHandoverToAirlineStatus(Object value) throws CustomException {
      return super.fetchList("fetchBagHandoverToAirline", null, sqlSession);
   }

   @Override
   public List<MailBagRequestModel> pushMailBagReceivingScanStatus(Object value) throws CustomException {
      return super.fetchList("fetchBagReceivingStatus", null, sqlSession);
   }

   @Override
   public List<FlightTouchDown> pushFlightTouchDownStatus(Object value) throws CustomException {
	   
	   return super.fetchList("fetchFlightTouchDownStatus", MultiTenantUtility.getAirportCityMap(""), sqlSession);
   }

   @Override
   public List<MailBagRequestModel> pushHandoverToDNATAStatus(Object value) throws CustomException {
      return super.fetchList("fetchHandoverToDNATAStatus", null, sqlSession);
   }

   @Override
   public List<MailBagRequestModel> pushOffloadStatus(Object value) throws CustomException {
      return super.fetchList("fetchOffloadStatus", null, sqlSession);
   }

   @Override
   public void logDataIntoAirmailEventTable(List<MailBagRequestModel> requestModel) throws CustomException {
      super.insertData("logDataIntoAirmailEventTable", requestModel, sqlSession);

   }

}
