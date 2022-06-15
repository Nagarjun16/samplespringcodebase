/**
 * This is a batch job component for auto expiring PO
 * 
 * @author NIIT Technologies Pvt. Ltd
 */
package com.ngen.cosys.application.job;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.application.model.AutoExpireDeliveryRequestModel;
import com.ngen.cosys.application.service.AutoExpireDeliveryRequestService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class AutoExpireDeliveryRequestJob extends AbstractCronJob {

   private static final Logger LOG = LoggerFactory.getLogger(AutoExpireDeliveryRequestJob.class);

   @Autowired
   AutoExpireDeliveryRequestService service;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.
    * JobExecutionContext)
    */
   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      try {
         // Get the list of shipments
         List<AutoExpireDeliveryRequestModel> shipments = this.service.getShipments();

         // Expire the PO
         for (AutoExpireDeliveryRequestModel t : shipments) {
            t.setLoggedInUser("AUTOBATCHJOB");
            t.setModifiedBy("AUTOBATCHJOB");
            this.service.expirePO(t);
         }
      } catch (CustomException ex) {
         LOG.error("Unable to expire PO for Shipment", ex);
      }
   }

}
