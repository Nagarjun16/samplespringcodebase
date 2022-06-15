package com.ngen.cosys.impbd.inboundflightmonitoring.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.inboundflightmonitoring.model.InboundFlightMonitoringModel;
import com.ngen.cosys.impbd.inboundflightmonitoring.model.InboundFlightMonitoringSerach;

public interface InboundFlightMonitoringService {

   List<InboundFlightMonitoringModel> getInboundFlightMonitoringInformation(
         InboundFlightMonitoringSerach inboundFlightMonitoringSearch) throws CustomException;
}