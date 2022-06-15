package com.ngen.cosys.TrendAnalysis.Service;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.TrendAnalysis.DAO.SQTrendAnalysisDAO;
import com.ngen.cosys.TrendAnalysis.Model.OutBoundFFMComplainceModel;
import com.ngen.cosys.TrendAnalysis.Model.SQTrendAnalysisModel;
import com.ngen.cosys.framework.exception.CustomException;

@Service
@Transactional
public class SQTrendAnalysisServiceImpl implements SQTrendAnalysisService {

   @Autowired
   SQTrendAnalysisDAO dao;

   @Override
   public void getAndInsertBookedAndUpliftedData(SQTrendAnalysisModel parameter) throws CustomException {
      parameter.setFromOffload(false);
      List<SQTrendAnalysisModel> bookedAndUpliftedOnSameFlightData = dao
            .getBookedAndUpliftedDataForAcceptance(parameter);
      for (SQTrendAnalysisModel shipmentwithFlight : bookedAndUpliftedOnSameFlightData) {
         insertUpdateTrendAnalysisShipments(shipmentwithFlight);
      }
      parameter.setFromCancelBooking(true);
      List<SQTrendAnalysisModel> bookedAndUpliftedOnCancelledShipment = dao
            .bookedAndUpliftedOnCancelledShipment(parameter);
      for (SQTrendAnalysisModel shipmentwithFlight : bookedAndUpliftedOnCancelledShipment) {
         shipmentwithFlight.setFromCancelBooking(true);
         insertUpdateTrendAnalysisShipments(shipmentwithFlight);
      }
      parameter.setFromCancelBooking(false);
      List<SQTrendAnalysisModel> bookedAndUpliftedFlightFreightOutData = dao
            .getBookedAndUpliftedDataForFreightOut(parameter);
      for (SQTrendAnalysisModel shipmentwithFlight : bookedAndUpliftedFlightFreightOutData) {
         insertUpdateTrendAnalysisShipments(shipmentwithFlight);
      }
      parameter.setFromOffload(true);
      List<SQTrendAnalysisModel> bookedAndUpliftedFlightOffloadData = dao.getBookedAndUpliftedDataForOffload(parameter);
      for (SQTrendAnalysisModel shipmentwithFlight : bookedAndUpliftedFlightOffloadData) {
         shipmentwithFlight.setFromOffload(true);
         insertUpdateTrendAnalysisShipments(shipmentwithFlight);
      }
   }

   private void insertUpdateTrendAnalysisShipments(SQTrendAnalysisModel shipmentwithFlight) throws CustomException {
      boolean checkShipmentAlreadyExists = dao.checkBookingAndUpliftShipmentAlreadyExists(shipmentwithFlight);
      if (!checkShipmentAlreadyExists) {
         dao.insertBookedAndUpliftedData(shipmentwithFlight);
      } else {
         dao.updateTrendAnalysis(shipmentwithFlight);
      }
   }

   @Override
   public void getOffloadingAtAirsideData() throws CustomException {
      List<SQTrendAnalysisModel> offloadingAtAirsideData = dao.getOffloadingAtAirsideData();
      for (SQTrendAnalysisModel shipmentwithFlight : offloadingAtAirsideData) {
         boolean checkShipmentAlreadyExists = dao.checkBookingAndUpliftShipmentAlreadyExists(shipmentwithFlight);
         if (!checkShipmentAlreadyExists) {
            dao.insertOffloadingAtAirsideData(shipmentwithFlight);
         } else {
            dao.updateTrendAnalysis(shipmentwithFlight);
         }
      }
   }

   @Override
   public SQTrendAnalysisModel getBookedAndUpliftedTonnageForFlightData(SQTrendAnalysisModel requestModel)
         throws CustomException {
      List<SQTrendAnalysisModel> bookedAndUpliftedTonnageForFlightData = dao
            .getBookedAndUpliftedTonnageForFlightData(requestModel);
      dao.insertBookedAndUpliftedTonnageForFlightData(bookedAndUpliftedTonnageForFlightData);
      return requestModel;
   }

   @Override
   public void updateSystemParameterForSQTrendAnalysis(SQTrendAnalysisModel parameter) throws CustomException {
      dao.updateSystemParameterForSQTrendAnalysis(parameter);
   }

   @Override
   public void getOutBoundFFMComplianceData(SQTrendAnalysisModel parameter) throws CustomException {
      List<OutBoundFFMComplainceModel> outBoundFFMCompliance = dao.getOutBoundFFMComplianceData();
      for (OutBoundFFMComplainceModel ffmFlights : outBoundFFMCompliance) {
         ffmFlights.setFromImport(false);
         boolean ffmDataExists = dao.checkFFMComplianceExportExistsOrNot(ffmFlights);
         if (ffmDataExists) {
            if (!StringUtils.isEmpty(ffmFlights.getFlightKey()) && !ObjectUtils.isEmpty(ffmFlights.getDateSTD())) {
               dao.updateExportImportDashboardForTrendAnalysis(ffmFlights);
            }
         } else {
            dao.insertExportImportDashboardForTrendAnalysis(ffmFlights);
         }
      }

      List<OutBoundFFMComplainceModel> offloadedShipmentsFlights = dao
            .getOffloadedShipmentsFlightForTrendAnalysis(parameter);
      for (OutBoundFFMComplainceModel departedAndOffloadedFlights : offloadedShipmentsFlights) {
         departedAndOffloadedFlights.setFromImport(false);
         boolean dataForOffloadExists = dao.checkFFMComplianceExportExistsOrNot(departedAndOffloadedFlights);
         if (dataForOffloadExists) {
            dao.updateExportImportDashboardForTrendAnalysis(departedAndOffloadedFlights);
         }
      }
      List<OutBoundFFMComplainceModel> importDashBoardData = dao.getImportDashBoardTrendAnalysis();
      for (OutBoundFFMComplainceModel impDashboardFlights : importDashBoardData) {
         boolean impDashBoardExists = dao.checkImportDashboardTrendExistsOrNot(impDashboardFlights);
         impDashboardFlights.setFromImport(true);
         if (impDashBoardExists) {
            if (!StringUtils.isEmpty(impDashboardFlights.getFlightKey())
                  && !ObjectUtils.isEmpty(impDashboardFlights.getDateSTA())) {
               dao.updateExportImportDashboardForTrendAnalysis(impDashboardFlights);
            }
         } else {
            dao.insertExportImportDashboardForTrendAnalysis(impDashboardFlights);
         }
      }

   }

   @Override
   public SQTrendAnalysisModel getTrendAnalysisParameters() throws CustomException {
      return dao.getTrendAnalysisParameters();
   }

   @Override
   public void getAndInsertBookedVsUpliftedTonnage(SQTrendAnalysisModel parameter) throws CustomException {
      List<OutBoundFFMComplainceModel> bookedVsUpliftedBookedTonnageData = dao
            .getBookedVsUpliftedBookedTonnageData(parameter);
      if (!CollectionUtils.isEmpty(bookedVsUpliftedBookedTonnageData)) {
         for (OutBoundFFMComplainceModel bookedTonnage : bookedVsUpliftedBookedTonnageData) {
            bookedTonnage.setFromImport(false);
            boolean bookedTonnageDataExists = dao.checkFFMComplianceExportExistsOrNot(bookedTonnage);
            if (bookedTonnageDataExists) {
               dao.updateExportImportDashboardForTrendAnalysis(bookedTonnage);
            } else {
               dao.insertExportImportDashboardForTrendAnalysis(bookedTonnage);
            }
         }
      }
      List<OutBoundFFMComplainceModel> bookedVsUpliftedDepartedTonnageData = dao
            .getBookedVsUpliftedDepartedTonnageData(parameter);
      for (OutBoundFFMComplainceModel departedTonnage : bookedVsUpliftedDepartedTonnageData) {
         departedTonnage.setFromImport(false);
         boolean departedTonnageDataExists = dao.checkFFMComplianceExportExistsOrNot(departedTonnage);
         if (departedTonnageDataExists) {
            dao.updateExportImportDashboardForTrendAnalysis(departedTonnage);
         }
      }
      List<OutBoundFFMComplainceModel> bookedVsUpliftedOffloadedTonnageData = dao
            .getBookedVsUpliftedOffloadedTonnageData(parameter);
      for (OutBoundFFMComplainceModel offloadedTonnage : bookedVsUpliftedOffloadedTonnageData) {
         offloadedTonnage.setFromImport(false);
         boolean offloadedTonnageDataExists = dao.checkFFMComplianceExportExistsOrNot(offloadedTonnage);
         if (offloadedTonnageDataExists) {
            dao.updateExportImportDashboardForTrendAnalysis(offloadedTonnage);
         }
      }
   }

}
