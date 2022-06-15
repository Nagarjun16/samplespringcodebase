package com.ngen.cosys.impbd.inboundflightmonitoring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.inboundflightmonitoring.dao.InboundFlightMonitoringDao;
import com.ngen.cosys.impbd.inboundflightmonitoring.model.InboundFlightMonitoringModel;
import com.ngen.cosys.impbd.inboundflightmonitoring.model.InboundFlightMonitoringSerach;

@Service
public class InboundFlightMonitoringServiceImpl implements InboundFlightMonitoringService {

   @Autowired
   private InboundFlightMonitoringDao dao;

   @Override
   public List<InboundFlightMonitoringModel> getInboundFlightMonitoringInformation(
         InboundFlightMonitoringSerach inboundFlightMonitoringSearch) throws CustomException {

      if (!StringUtils.isEmpty(inboundFlightMonitoringSearch.getFlight())
            && inboundFlightMonitoringSearch.getDate() == null) {
         inboundFlightMonitoringSearch.addError("emptyFlightDateErr", "date", ErrorType.ERROR);
      }
      if (inboundFlightMonitoringSearch.getDate() != null
            && StringUtils.isEmpty(inboundFlightMonitoringSearch.getFlight())) {
         inboundFlightMonitoringSearch.addError("emptyFlightkeyErr", "flight", ErrorType.ERROR);
      }

      if (inboundFlightMonitoringSearch.getFromDate() != null && inboundFlightMonitoringSearch.getToDate() == null) {
         inboundFlightMonitoringSearch.addError("SURVEYE2", "toDate", ErrorType.ERROR);
      }
      if (inboundFlightMonitoringSearch.getFromDate() == null && inboundFlightMonitoringSearch.getToDate() != null) {
         inboundFlightMonitoringSearch.addError("FROMDATE", "fromDate", ErrorType.ERROR);
      }

      if (!inboundFlightMonitoringSearch.getMessageList().isEmpty()) {
         throw new CustomException();
      }

      return dao.getInBoundFlightMonitoringInformation(inboundFlightMonitoringSearch);
   }

}