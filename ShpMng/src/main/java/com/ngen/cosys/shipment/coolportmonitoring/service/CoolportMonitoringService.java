package com.ngen.cosys.shipment.coolportmonitoring.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.coolportmonitoring.model.CoolportMonitoringDetail;
import com.ngen.cosys.shipment.coolportmonitoring.model.CoolportMonitoringSearch;

public interface CoolportMonitoringService {
   /**
    * @param searchparam
    * @return List<CoolportMonitoringDetail>
    * @throws CustomException
    */
   List<CoolportMonitoringDetail> fetch(CoolportMonitoringSearch searchparam) throws CustomException;
   
   List<CoolportMonitoringDetail> saveTemparatureRange(List<CoolportMonitoringSearch> searchparam) throws CustomException;
}
