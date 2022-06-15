/**
 * {@link ShipmentBreakdownCompleteDAOImpl}
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
 * Shipment Breakdown completed DAO Implementation
 * 
 * @author NIIT Technologies Ltd
 */
@Repository
public class ShipmentBreakdownCompleteDAOImpl extends BaseDAO implements ShipmentBreakdownCompleteDAO {

   private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentBreakdownCompleteDAO.class);
   
   @Qualifier("sqlSessionTemplate")
   @Autowired
   private SqlSession sqlSession;
   
   /**
    * @see com.ngen.cosys.event.notification.dao.ShipmentBreakdownCompleteDAO#getBreakdownNotCompletedFlights(com.ngen.cosys.event.notification.model.ImportFlightEvents)
    * 
    */
   @Override
   public List<FlightEvents> getBreakdownNotCompletedFlights(ImportFlightEvents importFlightEvents)
         throws CustomException {
      LOGGER.debug("Breakdown Not Complete Flight details - data extraction");
      return sqlSession.selectList(EventNotificationSQL.SELECT_INBOUND_NOT_COMPLETED_FLIGHT_DETAILS.getQueryId(),
            importFlightEvents);
   }

   /**
    * @see com.ngen.cosys.event.notification.dao.ShipmentBreakdownCompleteDAO#getBreakdownCompletedFlightsAfterTheLastExecutionTime(com.ngen.cosys.event.notification.model.ImportFlightEvents)
    * 
    */
   @Override
   public List<FlightEvents> getBreakdownCompletedFlightsAfterTheLastExecutionTime(
         ImportFlightEvents importFlightEvents) throws CustomException {
      LOGGER.debug("Breakdown Completed Flight details after the last execution time");
      return sqlSession.selectList(EventNotificationSQL.SELECT_INBOUND_COMPLETED_FLIGHT_DETAILS.getQueryId(),
            importFlightEvents);
   }

}
