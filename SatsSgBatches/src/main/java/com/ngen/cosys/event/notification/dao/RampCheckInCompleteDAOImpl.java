/**
 * {@link RampCheckInCompleteDAOImpl}
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
 * Ramp CheckIn completed DAO Implementation
 * 
 * @author NIIT Technologies Ltd
 */
@Repository
public class RampCheckInCompleteDAOImpl extends BaseDAO implements RampCheckInCompleteDAO {

   private static final Logger LOGGER = LoggerFactory.getLogger(RampCheckInCompleteDAO.class);
   
   @Qualifier("sqlSessionTemplate")
   @Autowired
   private SqlSession sqlSession;
   
   /**
    * @see com.ngen.cosys.event.notification.dao.RampCheckInCompleteDAO#getRampCheckInNotCompletedFlights(com.ngen.cosys.event.notification.model.ImportFlightEvents)
    */
   @Override
   public List<FlightEvents> getRampCheckInNotCompletedFlights(ImportFlightEvents importFlightEvents)
         throws CustomException {
      LOGGER.debug("Ramp Check In Not Completed Flight details - data extraction");
      return sqlSession.selectList(EventNotificationSQL.SELECT_INBOUND_NOT_COMPLETED_FLIGHT_DETAILS.getQueryId(),
            importFlightEvents);
   }

   /**
    * @see com.ngen.cosys.event.notification.dao.RampCheckInCompleteDAO#getRampCheckInCompletedFlightsAfterTheLastExecutionTime(com.ngen.cosys.event.notification.model.ImportFlightEvents)
    */
   @Override
   public List<FlightEvents> getRampCheckInCompletedFlightsAfterTheLastExecutionTime(
         ImportFlightEvents importFlightEvents) throws CustomException {
      LOGGER.debug("Ramp Check In Completed Flight details after the last execution time");
      return sqlSession.selectList(EventNotificationSQL.SELECT_INBOUND_COMPLETED_FLIGHT_DETAILS.getQueryId(),
            importFlightEvents);
   }
   
}
