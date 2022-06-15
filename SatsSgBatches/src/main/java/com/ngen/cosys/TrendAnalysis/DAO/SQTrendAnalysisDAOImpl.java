package com.ngen.cosys.TrendAnalysis.DAO;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.TrendAnalysis.Model.OutBoundFFMComplainceModel;
import com.ngen.cosys.TrendAnalysis.Model.SQTrendAnalysisModel;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

@Repository
public class SQTrendAnalysisDAOImpl extends BaseDAO implements SQTrendAnalysisDAO {

   @Autowired
   SqlSession session;

   @Override
   public List<SQTrendAnalysisModel> getBookedAndUpliftedDataForAcceptance(SQTrendAnalysisModel parameter)
         throws CustomException {
      return fetchList("sqlQueryTrendAnalysisForExportAcceptance", parameter, session);
   }

   @Override
   public void insertBookedAndUpliftedData(SQTrendAnalysisModel requestModel) throws CustomException {
      insertData("sqlInsertSQTrendAnalysis", requestModel, session);

   }

   @Override
   public List<SQTrendAnalysisModel> getOffloadingAtAirsideData() throws CustomException {
      return fetchList("sqlGetOffloadingAtAirSide", MultiTenantUtility.getAirportCityMap(null), session);
   }

   @Override
   public void insertOffloadingAtAirsideData(SQTrendAnalysisModel offloadingAtAirsideData) throws CustomException {
      insertData("sqlInsertSQTrendAnalysis", offloadingAtAirsideData, session);
   }

   @Override
   public List<SQTrendAnalysisModel> getBookedAndUpliftedTonnageForFlightData(SQTrendAnalysisModel requestModel)
         throws CustomException {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public void insertBookedAndUpliftedTonnageForFlightData(
         List<SQTrendAnalysisModel> bookedAndUpliftedTonnageForFlightData) throws CustomException {
      // TODO Auto-generated method stub

   }

   @Override
   public boolean checkBookingAndUpliftShipmentAlreadyExists(SQTrendAnalysisModel shipmentwithFlight)
         throws CustomException {
      return fetchObject("sqlCheckBookingAndUpliftShipmentAlreadyExists", shipmentwithFlight, session);
   }

   @Override
   public void updateTrendAnalysis(SQTrendAnalysisModel shipmentwithFlight) throws CustomException {
      updateData("sqlUpdateTrendAnalysis", shipmentwithFlight, session);
   }

   @Override
   public void updateSystemParameterForSQTrendAnalysis(SQTrendAnalysisModel parameter) throws CustomException {
      updateData("sqlUpdateTrendDepAsBkd", parameter, session);
      updateData("sqlUpdateSystemParameterForSqTrendAnalysis", LocalDate.now(), session);
   }

   @Override
   public List<OutBoundFFMComplainceModel> getOutBoundFFMComplianceData() throws CustomException {
      return fetchList("sqlGetOutBoundFFMComplianceExportDashBoard", MultiTenantUtility.getAirportCityMap(null),
            session);
   }

   @Override
   public boolean checkFFMComplianceExportExistsOrNot(OutBoundFFMComplainceModel ffmFlights) throws CustomException {
      return fetchObject("sqlCheckFFMComplianceExportExistsOrNot", ffmFlights, session);
   }

   @Override
   public void updateExportImportDashboardForTrendAnalysis(OutBoundFFMComplainceModel ffmFlights)
         throws CustomException {
      updateData("sqlUpdateExportImportDashboardForTrendAnalysis", ffmFlights, session);
   }

   @Override
   public void insertExportImportDashboardForTrendAnalysis(OutBoundFFMComplainceModel ffmFlights)
         throws CustomException {
      insertData("sqlInsertImportExportDashBoardForTrendAnalysis", ffmFlights, session);
   }

   @Override
   public List<OutBoundFFMComplainceModel> getImportDashBoardTrendAnalysis() throws CustomException {
      return fetchList("sqlGetImportDashBoardTrendAnalysis", MultiTenantUtility.getAirportCityMap(null), session);
   }

   @Override
   public boolean checkImportDashboardTrendExistsOrNot(OutBoundFFMComplainceModel impDashboardFlights)
         throws CustomException {
      return fetchObject("sqlCheckImportDashboardTrendExistsOrNot", impDashboardFlights, session);
   }

   @Override
   public SQTrendAnalysisModel getTrendAnalysisParameters() throws CustomException {
      return fetchObject("sqlGetParametersForTrendAnalysis", null, session);
   }

   @Override
   public List<SQTrendAnalysisModel> getBookedAndUpliftedDataForFreightOut(SQTrendAnalysisModel parameter)
         throws CustomException {
      return fetchList("sqlQueryTrendAnalysisForExportFreightOut", parameter, session);
   }

   @Override
   public List<SQTrendAnalysisModel> getBookedAndUpliftedDataForOffload(SQTrendAnalysisModel parameter)
         throws CustomException {
      return fetchList("sqlQueryTrendAnalysisForExportOffload", parameter, session);
   }

   @Override
   public List<SQTrendAnalysisModel> bookedAndUpliftedOnCancelledShipment(SQTrendAnalysisModel parameter)
         throws CustomException {
      return fetchList("sqlQueryTrendAnalysisForCancelledShipment", parameter, session);
   }

   @Override
   public List<OutBoundFFMComplainceModel> getOffloadedShipmentsFlightForTrendAnalysis(SQTrendAnalysisModel parameter)
         throws CustomException {
      return fetchList("sqlgetLatestTimeStampForOffloadInGivenTimeSpan", parameter, session);
   }

   @Override
   public List<OutBoundFFMComplainceModel> getBookedVsUpliftedBookedTonnageData(SQTrendAnalysisModel parameter)
         throws CustomException {
      return fetchList("sqlGetBKDVsUpliftedBookedTonnage", parameter, session);
   }

   @Override
   public List<OutBoundFFMComplainceModel> getBookedVsUpliftedDepartedTonnageData(SQTrendAnalysisModel parameter)
         throws CustomException {
      return fetchList("sqlGetBKDVsUpliftedDepartedTonnage", parameter, session);
   }

   @Override
   public List<OutBoundFFMComplainceModel> getBookedVsUpliftedOffloadedTonnageData(SQTrendAnalysisModel parameter)
         throws CustomException {
      return fetchList("sqlGetBKDVsUpliftedOffloadedTonnage", parameter, session);
   }

}
