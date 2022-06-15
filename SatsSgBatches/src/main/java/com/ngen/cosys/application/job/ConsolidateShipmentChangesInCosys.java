//package com.ngen.cosys.application.job;
//
//import java.util.List;
//
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.ngen.cosys.application.dao.ConsolidatedShipmentInfoDAO;
//import com.ngen.cosys.application.service.ConsolidatedShipmentInfoService;
//import com.ngen.cosys.application.service.ProduceManifestReconcillationStatementMessageService;
//import com.ngen.cosys.model.FlightModel;
//import com.ngen.cosys.model.ShipmentDataModel;
//import com.ngen.cosys.scheduler.esb.connector.ConnectorPublisher;
//import com.ngen.cosys.scheduler.job.AbstractCronJob;
//
//public class ConsolidateShipmentChangesInCosys extends AbstractCronJob {
//   @Autowired
//   private ConsolidatedShipmentInfoService consolidatedShipmentInfoService;
//   @Autowired
//   private ConsolidatedShipmentInfoDAO consolidatedShipmentInfoDAO;
// 
//   /*
//    * (non-Javadoc)
//    * 
//    * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.
//    * JobExecutionContext)
//    */
//
//   @Override
//   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//      super.executeInternal(jobExecutionContext);
//
// try {
//       
//    consolidatedShipmentInfoService.getFlightInfoFromCustoms();
//    List<FlightModel> flightData = consolidatedShipmentInfoService.getFlightInfoFromCustoms();
//    for (FlightModel flightdetails : flightData) {
//       List<ShipmentDataModel> shipmentData = consolidatedShipmentInfoDAO.getShipmentInfoForCustomsSubmittedShipment(flightdetails);
//       if (!shipmentData.isEmpty()) {
//
//        //  consolidatedShipmentInfoService.validateShipmentInfo(shipmentData);
//       }
//    }}catch(Exception e) {
//       
//    }
//   }
////}
//    
//
//
