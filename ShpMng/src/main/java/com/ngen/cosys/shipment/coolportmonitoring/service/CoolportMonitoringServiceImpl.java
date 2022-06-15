package com.ngen.cosys.shipment.coolportmonitoring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.coolportmonitoring.dao.CoolportMonitoringDAO;
import com.ngen.cosys.shipment.coolportmonitoring.model.CoolportMonitoringDetail;
import com.ngen.cosys.shipment.coolportmonitoring.model.CoolportMonitoringSearch;

/**
 * @author Nikhil.5.Gupta
 *
 */
@Service
public class CoolportMonitoringServiceImpl implements CoolportMonitoringService {

   @Autowired
   private CoolportMonitoringDAO coolportmonitoringDAO;

   /**
    * @param searchparam
    * @throws CustomException
    */
   private void validateSearch(CoolportMonitoringSearch searchparam) throws CustomException {
      if (searchparam.getDateTimeFrom() != null && searchparam.getDateTimeTo() != null
            && (searchparam.getDateTimeFrom().isAfter(searchparam.getDateTimeTo()))) {
         throw new CustomException("VAL001", "dateFrom", ErrorType.ERROR);
      }
      if (StringUtils.isEmpty(searchparam.getBy())) {
         throw new CustomException("CHOOSEBY", "by", ErrorType.ERROR);
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.coolportmonitoring.service.CoolportMonitoringService#
    * fetch(com.ngen.cosys.shipment.coolportmonitoring.model.
    * CoolportMonitoringSearch)
    */
   @Override
   public List<CoolportMonitoringDetail> fetch(CoolportMonitoringSearch searchparam) throws CustomException {
      validateSearch(searchparam);
      return coolportmonitoringDAO.fetch(searchparam);
   }

   @Override
   public List<CoolportMonitoringDetail> saveTemparatureRange(List<CoolportMonitoringSearch> searchparam)
         throws CustomException {
      return coolportmonitoringDAO.saveTemparatureRange(searchparam);
   }

}