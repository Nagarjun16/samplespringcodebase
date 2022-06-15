/**
 * {@link FlightDashboardService}
 * 
 * @copyright SATS Singapore 2020-21
 * @author NIIT
 */
package com.ngen.cosys.dashboard.service;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.dashboard.model.DashboardBatchLog;
import com.ngen.cosys.dashboard.model.ExportFlightData;
import com.ngen.cosys.dashboard.model.ImportFlightData;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * Flight Dashboard service
 * 
 * @author NIIT Technologies Ltd
 */
public interface FlightDashboardService {

   /**
    * INSERT Export Dashboard Batch Log
    * 
    * @param dashboardBatchLog
    * @throws CustomException
    */
   void insertExportDashboardBatchLog(DashboardBatchLog dashboardBatchLog) throws Exception;
   
   /**
    * UPDATE Export Dashboard Batch Log
    * 
    * @param dashboardBatchLog
    * @throws CustomException
    */
   void updateExportDashboardBatchLog(DashboardBatchLog dashboardBatchLog) throws Exception;
   
   /**
    * INSERT Import Dashboard Batch Log
    * 
    * @param dashboardBatchLog
    * @throws CustomException
    */
   void insertImportDashboardBatchLog(DashboardBatchLog dashboardBatchLog) throws Exception;
   
   /**
    * UPDATE Import Dashboard Batch Log
    * 
    * @param dashboardBatchLog
    * @throws CustomException
    */
   void updateImportDashboardBatchLog(DashboardBatchLog dashboardBatchLog) throws Exception;
   
   /**
    * GET Export Flights data
    * 
    * @return
    * @throws CustomException
    */
   List<ExportFlightData> getExportFlightsData() throws Exception;
   
   /**
    * INSERT Export Flights data
    * 
    * @param exportFlights
    * @param batchLogId
    * @throws CustomException
    */
   void insertExportFlightsData(List<ExportFlightData> exportFlights, BigInteger batchLogId) throws Exception;
   
   /**
    * GET Import Flights data
    * 
    * @return
    * @throws CustomException
    */
   List<ImportFlightData> getImportFlightsData() throws Exception;
   
   /**
    * INSERT Import Flights data
    * 
    * @param importFlights
    * @param batchLogId
    * @throws CustomException
    */
   void insertImportFlightsData(List<ImportFlightData> importFlights, BigInteger batchLogId) throws Exception;
   
}
