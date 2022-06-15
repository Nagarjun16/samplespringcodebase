package com.ngen.cosys.TrendAnalysis.DAO;

import java.util.List;

import com.ngen.cosys.TrendAnalysis.Model.OutBoundFFMComplainceModel;
import com.ngen.cosys.TrendAnalysis.Model.SQTrendAnalysisModel;
import com.ngen.cosys.framework.exception.CustomException;

public interface SQTrendAnalysisDAO {

   List<SQTrendAnalysisModel> getBookedAndUpliftedDataForAcceptance(SQTrendAnalysisModel parameter)
         throws CustomException;

   void insertBookedAndUpliftedData(SQTrendAnalysisModel shipment) throws CustomException;

   List<SQTrendAnalysisModel> getOffloadingAtAirsideData() throws CustomException;

   void insertOffloadingAtAirsideData(SQTrendAnalysisModel offloadingAtAirsideData) throws CustomException;

   List<SQTrendAnalysisModel> getBookedAndUpliftedTonnageForFlightData(SQTrendAnalysisModel requestModel)
         throws CustomException;

   void insertBookedAndUpliftedTonnageForFlightData(List<SQTrendAnalysisModel> bookedAndUpliftedTonnageForFlightData)
         throws CustomException;

   boolean checkBookingAndUpliftShipmentAlreadyExists(SQTrendAnalysisModel shipmentwithFlight) throws CustomException;

   void updateTrendAnalysis(SQTrendAnalysisModel shipmentwithFlight) throws CustomException;

   void updateSystemParameterForSQTrendAnalysis(SQTrendAnalysisModel parameter) throws CustomException;

   List<OutBoundFFMComplainceModel> getOutBoundFFMComplianceData() throws CustomException;

   boolean checkFFMComplianceExportExistsOrNot(OutBoundFFMComplainceModel ffmFlights) throws CustomException;

   void updateExportImportDashboardForTrendAnalysis(OutBoundFFMComplainceModel ffmFlights) throws CustomException;

   void insertExportImportDashboardForTrendAnalysis(OutBoundFFMComplainceModel ffmFlights) throws CustomException;

   List<OutBoundFFMComplainceModel> getImportDashBoardTrendAnalysis() throws CustomException;

   boolean checkImportDashboardTrendExistsOrNot(OutBoundFFMComplainceModel impDashboardFlights) throws CustomException;

   SQTrendAnalysisModel getTrendAnalysisParameters() throws CustomException;

   List<SQTrendAnalysisModel> getBookedAndUpliftedDataForFreightOut(SQTrendAnalysisModel parameter)
         throws CustomException;

   List<SQTrendAnalysisModel> getBookedAndUpliftedDataForOffload(SQTrendAnalysisModel parameter) throws CustomException;

   List<SQTrendAnalysisModel> bookedAndUpliftedOnCancelledShipment(SQTrendAnalysisModel parameter)
         throws CustomException;

   List<OutBoundFFMComplainceModel> getOffloadedShipmentsFlightForTrendAnalysis(SQTrendAnalysisModel parameter)
         throws CustomException;

   List<OutBoundFFMComplainceModel> getBookedVsUpliftedBookedTonnageData(SQTrendAnalysisModel parameter)
         throws CustomException;

   List<OutBoundFFMComplainceModel> getBookedVsUpliftedDepartedTonnageData(SQTrendAnalysisModel parameter)
         throws CustomException;

   List<OutBoundFFMComplainceModel> getBookedVsUpliftedOffloadedTonnageData(SQTrendAnalysisModel parameter)
         throws CustomException;

}
