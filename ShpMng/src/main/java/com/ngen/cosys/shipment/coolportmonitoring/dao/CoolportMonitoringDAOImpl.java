package com.ngen.cosys.shipment.coolportmonitoring.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;
import com.ngen.cosys.shipment.coolportmonitoring.model.CoolportMonitoringDetail;
import com.ngen.cosys.shipment.coolportmonitoring.model.CoolportMonitoringSearch;

/**
 * @author Nikhil.5.Gupta
 *
 */
@Repository("coolportMonitoringDAO")
public class CoolportMonitoringDAOImpl extends BaseDAO implements CoolportMonitoringDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSession sqlSessionCoolportShipment;

   @Autowired
   @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
   private SqlSession sqlSessionRIO;

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.coolportmonitoring.dao.CoolportMonitoringDAO#fetch(
    * com.ngen.cosys.shipment.coolportmonitoring.model.CoolportMonitoringSearch)
    */

   @Override
   public List<CoolportMonitoringDetail> fetch(CoolportMonitoringSearch coolportShipmentsRequestModel)
         throws CustomException {
      if ("import".equalsIgnoreCase(coolportShipmentsRequestModel.getBy())) {
         return super.fetchList("fetchcCoolportShipments", coolportShipmentsRequestModel, sqlSessionRIO);
      } else {
         return super.fetchList("fetchcCoolportShipmentsExport", coolportShipmentsRequestModel, sqlSessionRIO);
      }

   }

   @Override
   public List<CoolportMonitoringDetail> saveTemparatureRange(List<CoolportMonitoringSearch> searchparam)
         throws CustomException {
      super.updateData("updateTemparatureRange", searchparam, sqlSessionCoolportShipment);
      return new ArrayList<>();
   }
}