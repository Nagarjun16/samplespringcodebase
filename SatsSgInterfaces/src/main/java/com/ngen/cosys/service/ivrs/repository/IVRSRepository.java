/**
 * {@link IVRSRepository}
 * 
 * @copyright SATS Singapore
 * @author NIIT
 */
package com.ngen.cosys.service.ivrs.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.events.payload.ShipmentNotification;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.service.ivrs.constants.IVRSQueryConstants;
import com.ngen.cosys.service.ivrs.model.IVRSAWBRequest;
import com.ngen.cosys.service.ivrs.model.IVRSAWBResponse;
import com.ngen.cosys.service.ivrs.model.IVRSDataResponse;
import com.ngen.cosys.service.ivrs.model.IVRSFaxReportParams;
import com.ngen.cosys.service.ivrs.model.IVRSMessageLog;
import com.ngen.cosys.service.ivrs.model.IVRSRequest;

/**
 * IVRS Repository
 *  
 * @author NIIT Technologies
 */
@Repository
public class IVRSRepository extends BaseDAO {

   private static final Logger LOGGER = LoggerFactory.getLogger(IVRSRepository.class);
   
   @Autowired
   @Qualifier(BeanFactoryConstants.SESSION_TEMPLATE)
   private SqlSession sqlSession;
   
   @Autowired
   @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
   private SqlSession sqlSessionROI;
   
   /**
    * @param shipmentNotification
    * @return
    */
   public IVRSRequest getIVRSRequestData(ShipmentNotification shipmentNotification) {
      LOGGER.debug("IVRS Repository :: GET IVRS Request Data");
      return sqlSessionROI.selectOne(IVRSQueryConstants.SQL_SELECT_IVRS_FAX_AWB_REQUEST, shipmentNotification);
   }
   
   /**
    * @param shipmentNotification
    * @return
    */
   public IVRSRequest getFaxCopyRequestData(ShipmentNotification shipmentNotification) {
      LOGGER.debug("IVRS Repository :: GET FAX Copy Request Data");
      return sqlSessionROI.selectOne(IVRSQueryConstants.SQL_SELECT_IVRS_FAX_AWB_REQUEST, shipmentNotification);
   }
   
   /**
    * @param shipmentNotification
    * @return
    */
   public IVRSFaxReportParams getFaxReportParams(ShipmentNotification shipmentNotification) {
      LOGGER.debug("IVRS Repository :: GET FAX Report Params");
      Map<String,Object> paramMap = new HashMap<>();
      paramMap.put("tenantAirport", MultiTenantUtility.getAirportCodeFromContext());
      paramMap.put("shipmentNumber", shipmentNotification.getShipmentNumber());
      paramMap.put("shipmentDate", shipmentNotification.getShipmentDate());
      return sqlSessionROI.selectOne(IVRSQueryConstants.SQL_SELECT_IVRS_FAX_REPORT_PARAMS, paramMap);
   }
   
   /**
    * @param dataResponse
    * @return
    */
   public IVRSMessageLog getMessageLogDetail(IVRSDataResponse dataResponse) {
      LOGGER.debug("IVRS Repository :: SELECT Message Log Detail");
      return sqlSessionROI.selectOne(IVRSQueryConstants.SQL_SELECT_MESSAGE_LOG_DETAIL, dataResponse);
   }
   
   /**
    * @param dataRequest
    * @return
    */
   public IVRSAWBResponse getAirWayBillDetail(IVRSAWBRequest dataRequest) {
      LOGGER.debug("IVRS Repository :: GET AirWayBill Detail");
      return sqlSessionROI.selectOne(IVRSQueryConstants.SQL_SELECT_IVRS_ENQUIRE_AWB_REQUEST, dataRequest);
   }
   
}
