/**
 * CommonFlightIdDAOImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 04 Jan, 2018 NIIT -
 */
package com.ngen.cosys.shipment.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.shipment.model.CommonFlightId;

@Repository("commonFlightIdDAO")
public class CommonFlightIdDAOImpl extends BaseDAO implements CommonFlightIdDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSession sqlSessionShipment;

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.dao.CommonFlightIdDAO#getFlightId(com.ngen.cosys.
    * shipment.model.CommonFlightId)
    */
   @Override
   public String getFlightId(CommonFlightId commonFlight) throws CustomException {
      if (MultiTenantUtility.isTenantAirport(commonFlight.getSource())) {
         return super.fetchObject("sqlGetCommonExportFlightId", commonFlight, sqlSessionShipment);
      } else {
         String inboundFlight = super.fetchObject("sqlGetCommonImportFlightId", commonFlight, sqlSessionShipment);
         if (StringUtils.isEmpty(inboundFlight)) {
            // then attempt to get an outbound flight because for transshipment case for
            // outgoing flight irregularity can be captured for SSPD/OFLD etc.
            inboundFlight = super.fetchObject("sqlGetCommonExportFlightId", commonFlight, sqlSessionShipment);
         }

         return inboundFlight;
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.dao.CommonFlightIdDAO#getFlightKeyDate(com.ngen.cosys
    * .shipment.model.CommonFlightId)
    */
   @Override
   public List<CommonFlightId> getFlightKeyDate(CommonFlightId commonFlight) throws CustomException {
      if (MultiTenantUtility.isTenantAirport(commonFlight.getSource())) {
         return super.fetchList("sqlGetCommonExportFlightKeyDate", commonFlight, sqlSessionShipment);
      } else {
         List<CommonFlightId> flightDetails = super.fetchList("sqlGetCommonImportFlightKeyDate", commonFlight,
               sqlSessionShipment);
         if (CollectionUtils.isEmpty(flightDetails)) {
            // then attempt to get an outbound flight because for transshipment case for
            // outgoing flight irregularity can be captured for SSPD/OFLD etc.
            flightDetails = super.fetchList("sqlGetCommonExportFlightKeyDate", commonFlight, sqlSessionShipment);
         }

         return flightDetails;
      }
   }

}