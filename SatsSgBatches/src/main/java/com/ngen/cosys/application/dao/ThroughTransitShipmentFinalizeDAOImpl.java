/**
 * Repository implementation component which has business methods to process
 * through transit shipments finalized
 */
package com.ngen.cosys.application.dao;

import java.math.BigInteger;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.events.payload.InboundShipmentBreakDownCompleteStoreEvent;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.satssg.singpost.model.FlightTouchDown;

@Repository
public class ThroughTransitShipmentFinalizeDAOImpl extends BaseDAO implements ThroughTransitShipmentFinalizeDAO {

   @Autowired
   private SqlSessionTemplate sqlSessionTemplate;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.application.dao.ThroughTransitShipmentFinalizeDAO#get()
    */
   @Override
   public List<InboundShipmentBreakDownCompleteStoreEvent> get() throws CustomException {
	   return this.fetchList("sqlGetThroughTransitFinalizedShipments", MultiTenantUtility.getAirportCityMap(""), sqlSessionTemplate);
   }

   @Override
   public boolean isFlighHandledInSystem(InboundShipmentBreakDownCompleteStoreEvent event) throws CustomException {
      return fetchObject("sqlCheckFlightHandledInSystem_Batch", MultiTenantUtility.getAirportCityMap(event.getFlightId()), sqlSessionTemplate);
   }

   @Override
   public boolean isDataSyncCREnabled() throws CustomException {
      String flag = fetchObject("sqlCheckDataSyncCREnabledBatch", null, sqlSessionTemplate);
      return flag.equalsIgnoreCase("Y");
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.application.dao.ThroughTransitShipmentFinalizeDAO#
    * getDataSyncPartSuffix(java.math.BigInteger)
    */
   public List<String> getDataSyncPartSuffix(BigInteger shipmentId) throws CustomException {
	   
      return this.fetchList("sqlGetPartSuffixForTSM", shipmentId, sqlSessionTemplate);
   }

}