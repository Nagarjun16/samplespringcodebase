//package com.ngen.cosys.application.job;
//
//import java.util.List;
//
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.CollectionUtils;
//
//import com.ngen.cosys.application.dao.ConsolidatedShipmentInfoDAO;
//import com.ngen.cosys.application.service.ConsolidatedShipmentInfoService;
//import com.ngen.cosys.model.FlightModel;
//import com.ngen.cosys.model.ShipmentDataModel;
//import com.ngen.cosys.scheduler.job.AbstractCronJob;
//
//public class ConsolidatedShipmentInfoForCustomsJob extends AbstractCronJob {
//
//   private static final Logger LOGGER = LoggerFactory.getLogger(ConsolidatedShipmentInfoForCustomsJob.class);
//
//   @Autowired
//   private ConsolidatedShipmentInfoService consolidatedShipmentInfoService;
//
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
//      try {
//         List<FlightModel> flightData = consolidatedShipmentInfoService.getFlightInfo();
//         for (FlightModel flightdetails : flightData) {
//            if (null == flightdetails.getFlightType()) {
//               flightdetails.setFlightType("C");
//            }
//            List<ShipmentDataModel> customsShipmentsInfo = consolidatedShipmentInfoDAO
//                  .getCustomShipmentInfo(flightdetails);
//            if (!CollectionUtils.isEmpty(customsShipmentsInfo)) {
//               for (ShipmentDataModel shipment : customsShipmentsInfo) {
//                  try {
//                     shipment.setCustomsFlightId(flightdetails.getCustomsFlightId());
//                     consolidatedShipmentInfoService.processShipmentInfo(shipment);
//                  } catch (Exception e) {
//                     LOGGER.error("Exception while processing shipment information to MRS" + " - "
//                           + flightdetails.getFlightKey() + " - " + flightdetails.getFlightDate() + " - "
//                           + flightdetails.getFlightType() + shipment.getShipmentNumber(), e);
//                  }
//               }
//            }
//         }
//      } catch (Exception e) {
//         LOGGER.error("Exception while processing shipment information to MRS", e);
//      }
//   }
//}