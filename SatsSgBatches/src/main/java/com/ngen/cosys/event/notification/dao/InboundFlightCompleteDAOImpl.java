/**
 * {@link InboundFlightCompleteDAOImpl}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.event.notification.enums.EventNotificationSQL;
import com.ngen.cosys.event.notification.model.FlightEvents;
import com.ngen.cosys.event.notification.model.ImportFlightEvents;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * Inbound Flight completed DAO Implementation
 * 
 * @author NIIT Technologies Ltd
 */
@Repository
public class InboundFlightCompleteDAOImpl extends BaseDAO implements InboundFlightCompleteDAO {

   private static final Logger LOGGER = LoggerFactory.getLogger(InboundFlightCompleteDAO.class);
   
   @Qualifier("sqlSessionTemplate")
   @Autowired
   private SqlSession sqlSession;

   /**
    * @see com.ngen.cosys.event.notification.dao.InboundFlightCompleteDAO#getInboundNotCompletedFlights(com.ngen.cosys.event.notification.model.ImportFlightEvents)
    */
   @Override
   public List<FlightEvents> getInboundNotCompletedFlights(ImportFlightEvents importFlightEvents)
         throws CustomException {
      LOGGER.debug("Inbound Not Completed Flight details - data extraction");
      return sqlSession.selectList(EventNotificationSQL.SELECT_INBOUND_NOT_COMPLETED_FLIGHT_DETAILS.getQueryId(),
            importFlightEvents);
   }

   /**
    * @see com.ngen.cosys.event.notification.dao.InboundFlightCompleteDAO#getInboundCompletedFlightsAfterTheLastExecutionTime(com.ngen.cosys.event.notification.model.ImportFlightEvents)
    */
   @Override
   public List<FlightEvents> getInboundCompletedFlightsAfterTheLastExecutionTime(ImportFlightEvents importFlightEvents)
         throws CustomException {
      LOGGER.debug("Inbound Completed Flight details after the last execution time");
      return sqlSession.selectList(EventNotificationSQL.SELECT_INBOUND_COMPLETED_FLIGHT_DETAILS.getQueryId(),
            importFlightEvents);
   }
   
}
