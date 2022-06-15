package com.ngen.cosys.shipment.coolportmonitoring.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.coolportmonitoring.model.CoolportMonitoringDetail;
import com.ngen.cosys.shipment.coolportmonitoring.model.CoolportMonitoringSearch;


/**
 * @author Nikhil.5.Gupta
 *
 */
public interface CoolportMonitoringDAO {
   /**
    * @param coolportShipmentsRequestModel
    * @return List<CoolportMonitoringDetail>
    * @throws CustomException
    */

   List<CoolportMonitoringDetail> fetch(CoolportMonitoringSearch coolportShipmentsRequestModel)
         throws CustomException;
   
   List<CoolportMonitoringDetail> saveTemparatureRange(List<CoolportMonitoringSearch> searchparam) throws CustomException;
}