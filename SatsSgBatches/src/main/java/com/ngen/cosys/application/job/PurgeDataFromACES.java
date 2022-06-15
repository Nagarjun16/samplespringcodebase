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
//import com.ngen.cosys.model.CustomsMRSModel;
//import com.ngen.cosys.model.FlightModel;
//import com.ngen.cosys.scheduler.job.AbstractCronJob;
//
//public class PurgeDataFromACES extends AbstractCronJob {
//   @Autowired
//   private ConsolidatedShipmentInfoService consolidatedShipmentInfoService;
//   @Autowired
//   private ConsolidatedShipmentInfoDAO consolidatedShipmentInfoDAO;
//   @Override
//   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//      super.executeInternal(jobExecutionContext);
//
// try {
//    List<CustomsMRSModel> flightsToPurgeData=  consolidatedShipmentInfoService.getFlighttoPurgeFromAces();
//    for(CustomsMRSModel flight:flightsToPurgeData) {
//       consolidatedShipmentInfoService.purgeMRSInfo(flight);
//    }
//  
// }catch(Exception e) {
//    
// }
//}
//}