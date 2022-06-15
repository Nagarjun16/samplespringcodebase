/**
 * {@link FlightDashboardServiceImpl}
 * 
 * @copyright SATS Singapore 2020-21
 * @author NIIT
 */
package com.ngen.cosys.dashboard.service;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.dashboard.dao.FlightDashboardDAO;
import com.ngen.cosys.dashboard.model.DashboardBatchLog;
import com.ngen.cosys.dashboard.model.ExportFlightData;
import com.ngen.cosys.dashboard.model.ImportFlightData;

@Service
public class FlightDashboardServiceImpl implements FlightDashboardService {

   private static final Logger LOGGER = LoggerFactory.getLogger(FlightDashboardService.class);

   @Autowired
   FlightDashboardDAO flightDashboardDAO;
   
   /**
    * @see com.ngen.cosys.dashboard.service.FlightDashboardService#insertExportDashboardBatchLog(com.ngen.cosys.dashboard.model.DashboardBatchLog)
    */
   @Override
   public void insertExportDashboardBatchLog(DashboardBatchLog dashboardBatchLog) throws Exception {
      LOGGER.debug("Flight Dashboard Service - INSERT Export Dashboard Batch Log {}");
      flightDashboardDAO.insertExportBatchLog(dashboardBatchLog);
   }

   /**
    * @see com.ngen.cosys.dashboard.service.FlightDashboardService#updateExportDashboardBatchLog(com.ngen.cosys.dashboard.model.DashboardBatchLog)
    */
   @Override
   public void updateExportDashboardBatchLog(DashboardBatchLog dashboardBatchLog) throws Exception {
      LOGGER.debug("Flight Dashboard Service - UPDATE Export Dashboard Batch Log {}");
      flightDashboardDAO.updateExportBatchLog(dashboardBatchLog);
   }

   /**
    * @see com.ngen.cosys.dashboard.service.FlightDashboardService#insertImportDashboardBatchLog(com.ngen.cosys.dashboard.model.DashboardBatchLog)
    */
   @Override
   public void insertImportDashboardBatchLog(DashboardBatchLog dashboardBatchLog) throws Exception {
      LOGGER.debug("Flight Dashboard Service - INSERT Export Dashboard Batch Log {}");
      flightDashboardDAO.insertImportBatchLog(dashboardBatchLog);
   }

   /**
    * @see com.ngen.cosys.dashboard.service.FlightDashboardService#updateImportDashboardBatchLog(com.ngen.cosys.dashboard.model.DashboardBatchLog)
    */
   @Override
   public void updateImportDashboardBatchLog(DashboardBatchLog dashboardBatchLog) throws Exception {
      LOGGER.debug("Flight Dashboard Service - UPDATE Export Dashboard Batch Log {}");
      flightDashboardDAO.updateImportBatchLog(dashboardBatchLog);
   }
   
   /**
    * @see com.ngen.cosys.dashboard.service.FlightDashboardService#getExportFlightsData()
    */
   @Override
   public List<ExportFlightData> getExportFlightsData() throws Exception {
      LOGGER.debug("Flight Dashboard Service - GET Export Flights data {}");
      return flightDashboardDAO.getExportFlights();
   }
   
   /**
    * @see com.ngen.cosys.dashboard.service.FlightDashboardService#insertExportFlightsData(java.util.List,
    *      java.math.BigInteger)
    */
   @Override
   public void insertExportFlightsData(List<ExportFlightData> exportFlights, BigInteger batchLogId) throws Exception {
      LOGGER.debug("Flight Dashboard Service - INSERT Export Flights data {}");
      flightDashboardDAO.insertExportFlights(exportFlights, batchLogId);
   }
   
   /**
    * @see com.ngen.cosys.dashboard.service.FlightDashboardService#getImportFlightsData()
    */
   @Override
   public List<ImportFlightData> getImportFlightsData() throws Exception {
      LOGGER.debug("Flight Dashboard Service - GET Import Flights data {}");
      return flightDashboardDAO.getImportFlights();
   }
   
   /**
    * @see com.ngen.cosys.dashboard.service.FlightDashboardService#insertImportFlightsData(java.util.List,
    *      java.math.BigInteger)
    */
   @Override
   public void insertImportFlightsData(List<ImportFlightData> importFlights, BigInteger batchLogId) throws Exception {
      LOGGER.debug("Flight Dashboard Service - INSERT Import Flights data {}");
      flightDashboardDAO.insertImportFlights(importFlights, batchLogId);
   }
   
}
