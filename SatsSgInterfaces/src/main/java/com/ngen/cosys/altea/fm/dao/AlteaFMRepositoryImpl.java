/**
 * AlteaFMRepositoryImpl.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.dao;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.altea.fm.enums.AlteaFMSQL;
import com.ngen.cosys.altea.fm.model.DCSFMUpdateCargoFigures;
import com.ngen.cosys.events.payload.AlteaFMEvent;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;

/**
 * This interface is used for DCS Update Cargo DAO
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Repository
public class AlteaFMRepositoryImpl extends BaseDAO implements AlteaFMRepository {

   private static final Logger LOGGER = LoggerFactory.getLogger(AlteaFMRepository.class);
   
   @Autowired
   @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
   private SqlSession sqlSession;
   
   /**
    * @see com.ngen.cosys.altea.fm.dao.AlteaFMRepository#isMessageAddressingSetupConfigured(com.ngen.cosys.events.payload.AlteaFMEvent)
    * 
    */
   @Override
   public boolean isMessageAddressingSetupConfigured(AlteaFMEvent alteaFMEvent) throws CustomException {
      LOGGER.warn("AlteaFM Message addressing setup configuration {}");
      return sqlSession.selectOne(AlteaFMSQL.SQL_VERIFY_ALTEA_FM_CONFIGURED.getQueryId(), alteaFMEvent);
   }

   /**
    * @see com.ngen.cosys.altea.fm.dao.AlteaFMRepository#getConfiguredPerFlightMessageCount(java.lang.String)
    * 
    */
   @Override
   public String getConfiguredPerFlightMessageCount(String param) throws CustomException {
      LOGGER.warn("AlteaFM Configured Per Flight message count {}");
      return sqlSession.selectOne(AlteaFMSQL.SQL_SELECT_ALTEA_FM_PER_FLIGHT_MESSAGE_COUNT.getQueryId(), param);
   }

   /**
    * @see com.ngen.cosys.altea.fm.dao.AlteaFMRepository#getSENTMessageCountByFlight(com.ngen.cosys.events.payload.AlteaFMEvent)
    * 
    */
   @Override
   public int getSENTMessageCountByFlight(AlteaFMEvent alteaFMEvent) throws CustomException {
      LOGGER.warn("AlteaFM SENT Message count by Flight, Message Type & Sub Message Type {}");
      return sqlSession.selectOne(AlteaFMSQL.SQL_SELECT_ALTEA_FM_SENT_MESSAGE_COUNT_BY_FLIGHT.getQueryId(),
            alteaFMEvent);
   }
   
   /**
    * @see com.ngen.cosys.altea.fm.dao.AlteaFMRepository#getAlteaFMEventSourcePayload(com.ngen.cosys.events.payload.AlteaFMEvent)
    * 
    */
   @Override
   public DCSFMUpdateCargoFigures getAlteaFMEventSourcePayload(AlteaFMEvent alteaFMEvent) throws CustomException {
      LOGGER.debug("AlteaFM Event Source Payload {}");
      return super.fetchObject(AlteaFMSQL.SQL_SELECT_ALTEA_FM_CARGO_DATA.getQueryId(), alteaFMEvent, sqlSession);
   }

}
