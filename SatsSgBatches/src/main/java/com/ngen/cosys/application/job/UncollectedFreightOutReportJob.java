package com.ngen.cosys.application.job;

import java.time.LocalDateTime;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.scheduler.job.AbstractCronJob;
import com.ngen.cosys.timezone.util.TenantZoneTime;
import com.ngen.cosys.uncollectedfreightout.model.UncollectedFreightoutShipmentModel;
import com.ngen.cosys.uncollectedfreightout.service.UncollectedFreightoutService;

@DisallowConcurrentExecution
public class UncollectedFreightOutReportJob extends AbstractCronJob {

	@Autowired
	private UncollectedFreightoutService uncollectedFreightoutService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.
	 * JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		UncollectedFreightoutShipmentModel model = new UncollectedFreightoutShipmentModel();
		LocalDateTime currentUTCTime = LocalDateTime.now();
		LocalDateTime currentZoneTime = TenantZoneTime.getZoneDateTime(currentUTCTime,
				TenantContext.get().getTenantId());
		model.setDate(currentZoneTime);
		try {
			uncollectedFreightoutService.sendDateForUncollectedFreightout(model);
		} catch (CustomException e) {

		}
	}

}