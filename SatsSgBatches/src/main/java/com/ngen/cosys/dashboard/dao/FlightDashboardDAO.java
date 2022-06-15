/**
 * {@link FlightDashboardDAO}
 * 
 * @copyright SATS Singapore 2020-21
 * @author NIIT
 */
package com.ngen.cosys.dashboard.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.dashboard.model.DashboardBatchLog;
import com.ngen.cosys.dashboard.model.ExportFlightData;
import com.ngen.cosys.dashboard.model.ImportFlightData;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * Flight Dashboard DAO
 * 
 * @author NIIT Technologies Ltd
 */
public interface FlightDashboardDAO {

   /**
    * INSERT Export Dashboard Batch Log
    * 
    * @param flightsMonitoring
    * @throws CustomException
    */
   void insertExportBatchLog(DashboardBatchLog dashboardBatchLog) throws Exception;

   /**
    * UPDATE Export Dashboard Batch Log
    * 
    * @param flightsMonitoring
    * @throws CustomException
    */
   void updateExportBatchLog(DashboardBatchLog dashboardBatchLog) throws Exception;

   /**
    * INSERT Import Dashboard Batch Log
    * 
    * @param flightsMonitoring
    * @throws CustomException
    */
   void insertImportBatchLog(DashboardBatchLog dashboardBatchLog) throws Exception;

   /**
    * UPDATE Import Dashboard Batch Log
    * 
    * @param flightsMonitoring
    * @throws CustomException
    */
   void updateImportBatchLog(DashboardBatchLog dashboardBatchLog) throws Exception;
   
   /**
    * GET Export Flights
    * 
    * @return
    * @throws CustomException
    */
   List<ExportFlightData> getExportFlights() throws Exception;
   
   /**
    * INSERT Export Flights
    * 
    * @param exportFlights
    * @param batchLogId
    * @throws CustomException
    */
   void insertExportFlights(List<ExportFlightData> exportFlights, BigInteger batchLogId) throws Exception;
   
   /**
    * GET Import Flights
    * 
    * @return
    * @throws CustomException
    */
   List<ImportFlightData> getImportFlights() throws Exception;
   
   /**
    * INSERT Import Flights
    * 
    * @param importFlights
    * @param batchLogId
    * @throws CustomException
    */
   void insertImportFlights(List<ImportFlightData> importFlights, BigInteger batchLogId) throws Exception;
   
}
