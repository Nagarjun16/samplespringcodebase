package com.ngen.cosys.TrendAnalysis.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ngen.cosys.TrendAnalysis.Model.SQTrendAnalysisModel;
import com.ngen.cosys.TrendAnalysis.Service.SQTrendAnalysisService;
import com.ngen.cosys.framework.exception.CustomException;

@RestController
public class SQTrendAnalysisController {

   @Autowired
   SQTrendAnalysisService service;

   private static final Logger logger = LoggerFactory.getLogger(SQTrendAnalysisController.class);

   @RequestMapping(value = "/api/sqtrendanalysis/executealloperation", method = RequestMethod.POST)
   public String sqTrendAnalysis() throws CustomException {
      logger.warn("before processing the trend analysis job");
      SQTrendAnalysisModel parameter = service.getTrendAnalysisParameters();
      logger.warn("parameter for trend analysis job", parameter.getFromDate() + ", " + parameter.getToDate());
      logger.warn("started executing the bookedvsuplifted and offloading/cancelled shipments");
      service.getAndInsertBookedAndUpliftedData(parameter);
      logger.warn("finished the bookedvsuplifted and offloading/cancelled shipments");
      // service.getOffloadingAtAirsideData();
      logger.warn("started executing the bookedvsuplifted tonnage");
      service.getAndInsertBookedVsUpliftedTonnage(parameter);
      logger.warn("finished the bookedvsuplifted tonnage");
      logger.warn("Started executing the Import and export flights");
      service.getOutBoundFFMComplianceData(parameter);
      logger.warn("finished the execution Import and export flights");
      service.updateSystemParameterForSQTrendAnalysis(parameter);
      return "data executed successfully";
   }
}
