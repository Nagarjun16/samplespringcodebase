package com.ngen.cosys.TrendAnalysis.Service;

import com.ngen.cosys.TrendAnalysis.Model.SQTrendAnalysisModel;
import com.ngen.cosys.framework.exception.CustomException;

public interface SQTrendAnalysisService {

   void getAndInsertBookedAndUpliftedData(SQTrendAnalysisModel parameter) throws CustomException;

   void getOffloadingAtAirsideData() throws CustomException;

   SQTrendAnalysisModel getBookedAndUpliftedTonnageForFlightData(SQTrendAnalysisModel requestModel)
         throws CustomException;

   void updateSystemParameterForSQTrendAnalysis(SQTrendAnalysisModel parameter) throws CustomException;

   void getOutBoundFFMComplianceData(SQTrendAnalysisModel parameter) throws CustomException;

   SQTrendAnalysisModel getTrendAnalysisParameters() throws CustomException;

   void getAndInsertBookedVsUpliftedTonnage(SQTrendAnalysisModel parameter)throws CustomException;

}
