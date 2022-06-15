/**
 * {@link FlightDashboardDAOImpl}
 * 
 * @copyright SATS Singapore 2020-21
 * @author NIIT
 */
package com.ngen.cosys.dashboard.dao;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.dashboard.enums.DashboardSQL;
import com.ngen.cosys.dashboard.model.DashboardBatchLog;
import com.ngen.cosys.dashboard.model.ExportFlightData;
import com.ngen.cosys.dashboard.model.ImportFlightData;
import com.ngen.cosys.dashboard.util.FlightDashboardUtils;
import com.ngen.cosys.model.ICSOperativeFlightRequestModel;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

/**
 * Flight Dashboard DAO Implementation
 * 
 * @author NIIT Technologies Ltd
 */
@Repository
public class FlightDashboardDAOImpl implements FlightDashboardDAO {

   private static final Logger LOGGER = LoggerFactory.getLogger(FlightDashboardDAO.class);
   
   @Autowired
   @Qualifier(BeanFactoryConstants.SESSION_TEMPLATE)
   private SqlSession sqlSession;
   
   @Autowired
   @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
   private SqlSession sqlSessionROI;

   /**
    * @see com.ngen.cosys.dashboard.dao.FlightDashboardDAO#insertExportBatchLog(com.ngen.cosys.dashboard.model.DashboardBatchLog)
    */
   @Override
   public void insertExportBatchLog(DashboardBatchLog dashboardBatchLog) throws Exception {
      LOGGER.debug("Flight Dashboard DAO - INSERT Export Batch Log {}");
      sqlSession.insert(DashboardSQL.INSERT_EXPORT_DASHBOARD_BATCH_LOG.getQueryId(), dashboardBatchLog);
   }

   /**
    * @see com.ngen.cosys.dashboard.dao.FlightDashboardDAO#updateExportBatchLog(com.ngen.cosys.dashboard.model.DashboardBatchLog)
    */
   @Override
   public void updateExportBatchLog(DashboardBatchLog dashboardBatchLog) throws Exception {
      LOGGER.debug("Flight Dashboard DAO - UPDATE Export Batch Log {}");
      sqlSession.update(DashboardSQL.UPDATE_EXPORT_DASHBOARD_BATCH_LOG.getQueryId(), dashboardBatchLog);
   }

   /**
    * @see com.ngen.cosys.dashboard.dao.FlightDashboardDAO#insertImportBatchLog(com.ngen.cosys.dashboard.model.DashboardBatchLog)
    */
   @Override
   public void insertImportBatchLog(DashboardBatchLog dashboardBatchLog) throws Exception {
      LOGGER.debug("Flight Dashboard DAO - INSERT Import Batch Log {}");
      sqlSession.insert(DashboardSQL.INSERT_IMPORT_DASHBOARD_BATCH_LOG.getQueryId(), dashboardBatchLog);
   }

   /**
    * @see com.ngen.cosys.dashboard.dao.FlightDashboardDAO#updateImportBatchLog(com.ngen.cosys.dashboard.model.DashboardBatchLog)
    */
   @Override
   public void updateImportBatchLog(DashboardBatchLog dashboardBatchLog) throws Exception {
      LOGGER.debug("Flight Dashboard DAO - UPDATE Import Batch Log {}");
      sqlSession.update(DashboardSQL.UPDATE_IMPORT_DASHBOARD_BATCH_LOG.getQueryId(), dashboardBatchLog);
   }
   
   /**
    * GET Export Flights Data
    * 
    * @see com.ngen.cosys.dashboard.dao.FlightDashboardDAO#getExportFlights()
    */
   @Override
   public List<ExportFlightData> getExportFlights() throws Exception {
      LOGGER.debug("Flight Dashboard DAO - GET Export Flights {}");
         return sqlSessionROI.selectList(DashboardSQL.SELECT_EXPORT_FLIGHTS_DATA.getQueryId(),MultiTenantUtility.getAirportCityMap(""));
   }

   /**
    * INSERT Export Flights Data
    * 
    * @see com.ngen.cosys.dashboard.dao.FlightDashboardDAO#insertExportFlights(java.util.List, java.math.BigInteger)
    */
   @Override
   public void insertExportFlights(List<ExportFlightData> exportFlights, BigInteger batchLogId) throws Exception {
      LOGGER.debug("Flight Dashboard DAO - INSERT Export Flights {}");
      // Delete operation
      LocalDateTime startTime = FlightDashboardUtils.getCurrentZoneTime();
      sqlSession.delete(DashboardSQL.DELETE_EXPORT_FLIGHTS_DATA.getQueryId());
      LOGGER.warn("Flight Dashboard DAO - DELETE Export Flights data - START TIME - {}, END TIME - {}", startTime,
            FlightDashboardUtils.getCurrentZoneTime());
      // Insert operation
      startTime = FlightDashboardUtils.getCurrentZoneTime();
      for (ExportFlightData exportFlight : exportFlights) {
         exportFlight.setDashboardBatchLogId(batchLogId);
         sqlSession.insert(DashboardSQL.INSERT_EXPORT_FLIGHTS_DATA.getQueryId(), exportFlight);
      }
      LOGGER.warn("Flight Dashboard DAO - INSERT Export Flights data - START TIME - {}, END TIME - {}", startTime,
            FlightDashboardUtils.getCurrentZoneTime());
   }
   
   /**
    * GET Import Flights Data
    * 
    * @see com.ngen.cosys.dashboard.dao.FlightDashboardDAO#getImportFlights()
    */
   @Override
   public List<ImportFlightData> getImportFlights() throws Exception {
      LOGGER.debug("Flight Dashboard DAO - GET Import Flights {}");
      return sqlSessionROI.selectList(DashboardSQL.SELECT_IMPORT_FLIGHTS_DATA.getQueryId(),MultiTenantUtility.getAirportCityMap(""));
   }

   /**
    * INSERT Import Flight Data
    * 
    * @see com.ngen.cosys.dashboard.dao.FlightDashboardDAO#insertImportFlights(java.util.List, java.math.BigInteger)
    */
   @Override
   public void insertImportFlights(List<ImportFlightData> importFlights, BigInteger batchLogId) throws Exception {
      LOGGER.debug("Flight Dashboard DAO - INSERT Import Flights {}");
      // Delete operation
      LocalDateTime startTime = FlightDashboardUtils.getCurrentZoneTime();
      sqlSession.delete(DashboardSQL.DELETE_IMPORT_FLIGHTS_DATA.getQueryId());
      LOGGER.warn("Flight Dashboard DAO - DELETE Import Flights data - START TIME - {}, END TIME - {}", startTime,
            FlightDashboardUtils.getCurrentZoneTime());
      // Insert operation
      startTime = FlightDashboardUtils.getCurrentZoneTime();
      for (ImportFlightData importFlight : importFlights) {
         importFlight.setDashboardBatchLogId(batchLogId);
         sqlSession.insert(DashboardSQL.INSERT_IMPORT_FLIGHTS_DATA.getQueryId(), importFlight);
      }
      LOGGER.warn("Flight Dashboard DAO - INSERT Import Flights data - START TIME - {}, END TIME - {}", startTime,
            FlightDashboardUtils.getCurrentZoneTime());
   }

}
