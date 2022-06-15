/**
 * 
 */
package com.ngen.cosys.message.resend.job;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.events.payload.InterfaceMessageLogModel;
import com.ngen.cosys.events.stream.router.CargoMessageInterfaceEventStreamRouter;
import com.ngen.cosys.message.resend.service.RefireFailedEventsService;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

/**
 * This job will re-fire the failed events at configured time Number of retry
 * and the time will be configurable.
 * 
 * 
 * @author NIIT Technologies ltd
 *
 */
public class RefireFailedEventsJob extends AbstractCronJob {

	@Autowired
	RefireFailedEventsService refireFailedEventsService;

	@Autowired
	CargoMessageInterfaceEventStreamRouter cargoMessageInterfaceEventStreamRouter;
	

	private static final Logger LOGGER = LoggerFactory.getLogger(RefireFailedEventsJob.class);

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		// TODO Auto-generated method stub
		LOGGER.info("RefireFailedEventsJob --> started ");
		// get the failed message for last 'N' configured minutes
		List<InterfaceMessageLogModel> failedEventList = new ArrayList<>();
		try {
			failedEventList = refireFailedEventsService.getfailedEvents();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// re-fire the event.
		for (InterfaceMessageLogModel interfaceMessageLogModel : failedEventList) {
			// the last fired time
			if (interfaceMessageLogModel.getLastFiredTimeDiff() != null && interfaceMessageLogModel
					.getLastFiredTimeDiff().compareTo(interfaceMessageLogModel.getReFireConfigTime()) >= 1) {
			


				try{
					cargoMessageInterfaceEventStreamRouter.route(interfaceMessageLogModel.getEventPayload(),
							interfaceMessageLogModel.getEventType(), interfaceMessageLogModel.getEventLogId());
					}catch(Exception e) {
						LOGGER.info(e.toString());
					}
				// update the re try count and time.
				refireFailedEventsService.updateRetryCount(interfaceMessageLogModel);
			}

		}
		LOGGER.info("RefireFailedEventsJob --> Completed ");
	}

}
