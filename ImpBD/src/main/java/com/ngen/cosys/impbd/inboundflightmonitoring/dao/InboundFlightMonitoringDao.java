package com.ngen.cosys.impbd.inboundflightmonitoring.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.inboundflightmonitoring.model.InboundFlightMonitoringModel;
import com.ngen.cosys.impbd.inboundflightmonitoring.model.InboundFlightMonitoringSerach;

public interface InboundFlightMonitoringDao {
   List<InboundFlightMonitoringModel> getInBoundFlightMonitoringInformation(
         InboundFlightMonitoringSerach inboundFlightMonitoringSerach) throws CustomException;

   InboundFlightMonitoringModel fetchFlightFromTranshipment(InboundFlightMonitoringSerach inboundFlightMonitoringSerach)
         throws CustomException;
}