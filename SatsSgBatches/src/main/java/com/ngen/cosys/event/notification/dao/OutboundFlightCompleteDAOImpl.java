/**
 * {@link OutboundFlightCompleteDAOImpl}
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
import com.ngen.cosys.event.notification.model.ExportFlightEvents;
import com.ngen.cosys.event.notification.model.FlightEvents;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * Outbound Flight completed DAO Implementation
 * 
 * @author NIIT Technologies Ltd
 */
@Repository
public class OutboundFlightCompleteDAOImpl extends BaseDAO implements OutboundFlightCompleteDAO {

   private static final Logger LOGGER = LoggerFactory.getLogger(OutboundFlightCompleteDAO.class);
   
   @Qualifier("sqlSessionTemplate")
   @Autowired
   private SqlSession sqlSession;
   
   /**
    * @see com.ngen.cosys.event.notification.dao.OutboundFlightCompleteDAO#getOutboundNotCompletedFlights(com.ngen.cosys.event.notification.model.ExportFlightEvents)
    */
   @Override
   public List<FlightEvents> getOutboundNotCompletedFlights(ExportFlightEvents exportFlightEvents)
         throws CustomException {
      LOGGER.debug("Outbound Not Completed Flight details - data extraction");
      return sqlSession.selectList(EventNotificationSQL.SELECT_OUTBOUND_NOT_COMPLETED_FLIGHT_DETAILS.getQueryId(),
            exportFlightEvents);
   }

   /**
    * @see com.ngen.cosys.event.notification.dao.OutboundFlightCompleteDAO#getOutboundCompletedFlightsAfterTheLastExecutionTime(com.ngen.cosys.event.notification.model.ExportFlightEvents)
    */
   @Override
   public List<FlightEvents> getOutboundCompletedFlightsAfterTheLastExecutionTime(ExportFlightEvents exportFlightEvents)
         throws CustomException {
      LOGGER.debug("Outbound Completed Flight details after the last execution time");
      return sqlSession.selectList(EventNotificationSQL.SELECT_OUTBOUND_COMPLETED_FLIGHT_DETAILS.getQueryId(),
            exportFlightEvents);
   }

}
