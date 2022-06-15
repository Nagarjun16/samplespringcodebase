/**
 * 
 * @copyright SATS Singapore 2020-21
 * @author NIIT
 */
package com.ngen.cosys.email.job;

import java.time.LocalDateTime;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.email.constants.MailConstants;
import com.ngen.cosys.email.model.MailResendDetail;
import com.ngen.cosys.email.processor.MailResendProcessor;
import com.ngen.cosys.email.service.MailResendService;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.scheduler.job.AbstractCronJob;
import com.ngen.cosys.timezone.util.TenantZoneTime;

/**
 * Mail Resend Job
 * 
 * @author NIIT Technologies Ltd
 */
@Component(value = MailConstants.EMAIL_RESEND_JOB)
public class MailResendJob extends AbstractCronJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailResendJob.class);

	@Autowired
	MailResendService mailResendService;

	@Autowired
	MailResendProcessor mailResendProcessor;

	/**
	 * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		this.resend();
	}

	public void resend() {
		LocalDateTime startTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(),
				TenantContext.get().getTenantId());
		boolean isResendTaskCompleted = false;
		boolean isInvalidMailAddressTaskCompleted = false;
		try {
			List<MailResendDetail> failedMailDetails = mailResendService.getListOfFailedMailDetails();
			if (!CollectionUtils.isEmpty(failedMailDetails)) {
				LOGGER.warn("Mail Resend Job :: Failed Mail Details - Size - {}", failedMailDetails.size());
				mailResendProcessor.resendFailedMailDetails(failedMailDetails);
				// Update the status
				mailResendService.updateResendMailStatus(failedMailDetails);
			} else {
				LOGGER.warn("Mail Resend Job :: Failed Mail Details data is EMPTY {}");
			}
			// Invalid Mail Address
			String notificationAddress = mailResendService.getNotifyMailAddressForRejectedMails();
			if (!StringUtils.isEmpty(notificationAddress)) {
				List<MailResendDetail> invalidMailAddressDetails = mailResendService
						.getListOfFailedWrongMailAddressDetails();
				if (!CollectionUtils.isEmpty(invalidMailAddressDetails)) {
					LOGGER.warn("Mail Resend Job :: Invalid Mail Address configured Details - Size - {}",
							invalidMailAddressDetails.size());
					mailResendProcessor.notifyInvalidMailAddressesDetails(invalidMailAddressDetails,
							notificationAddress);
					mailResendService.updateResendMailStatus(invalidMailAddressDetails);
				} else {
					LOGGER.warn("Mail Resend Job :: Invalid Mail Address configured Details data is EMPTY {}");
				}
			}
		} catch (Exception ex) {
			LOGGER.error(
					"Mail Resending Job exception occurred :: Resend Task Completed - {}, Invalid Mail Address Task Completed - {}, Stack Trace - {}",
					String.valueOf(isResendTaskCompleted), String.valueOf(isInvalidMailAddressTaskCompleted), ex);
		}
		LocalDateTime endTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), TenantContext.get().getTenantId());
		LOGGER.warn("Mail Resending Job execution completed :: START TIME - {}, END TIME - {}", startTime, endTime);
	}

}
