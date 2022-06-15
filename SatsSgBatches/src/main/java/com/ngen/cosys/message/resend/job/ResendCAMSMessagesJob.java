/**
 * 
 */
package com.ngen.cosys.message.resend.job;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ibsplc.icargo.business.mailtracking.defaults.types.standard.SaveMailDetailsRequestType;
import com.ibsplc.icargo.business.mailtracking.defaults.types.standard.SaveMailDetailsResponseType;
import com.ngen.cosys.AirmailStatus.Service.CamsConnectorConfigService;
import com.ngen.cosys.events.payload.AirmailStatusEvent;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.events.producer.SendEmailEventProducer;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.service.ResendCAMSMessagesService;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

/**
 * @author Pavadesh.B
 *
 */
public class ResendCAMSMessagesJob extends AbstractCronJob {

	@Autowired
	private ResendCAMSMessagesService service;

	@Autowired
	private CamsConnectorConfigService camsService;
	
	@Autowired
	private SendEmailEventProducer publisher;

	private static final Logger LOGGER = LoggerFactory.getLogger(ResendCAMSMessagesJob.class);

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		LOGGER.info("ResendCAMSMessagesJob --> executeInternal() start");
		List<AirmailStatusEvent> resendCAMSList = new ArrayList<>();
		
		boolean updateMailFlag = false;
		try {
			resendCAMSList = service.fetchFailedCAMSMessages();
			for (AirmailStatusEvent event : resendCAMSList) {
				
				if(event.getRetryCount() == event.getConfiguredRetryCount()) {
					this.sendMail();
					updateMailFlag =true;
					
					break;
				}
				else {
					// check for long sleep flag
					if (event.isLongSleepFalg()) {
						
						if (event.getRetryCount() > 1) {
							//  long sleep
							if (event.getTimeDiffFromLastUpdated() >= event.getLongsleepTime() && event.getRetryCount() < event.getConfiguredRetryCount()) {
								this.resendMessage(event);
							}
						} else {
							//  short sleep
							if (event.getTimeDiffFromLastUpdated() >= event.getShortSleepTime()) {
								this.resendMessage(event);
							}
						}

					} else {
						// send message
						this.resendMessage(event);
					}
				}
				

			}
			if(updateMailFlag) {
				// update the count, so in next run it will not send mail again
				for (AirmailStatusEvent event : resendCAMSList) {
					service.updateCAMSEventLog(event);
				}
				
			}
		} catch (CustomException e) {
			e.printStackTrace();
			LOGGER.debug(e.getMessage());
		}

	}

	private <T> T unMarshall(String payload, Class<T> requiredType) {
		T result = null;
		XmlMapper objectMapper = new XmlMapper();
		//
		try {
			result = objectMapper.readValue(payload, requiredType);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return result;
	}


	private void resendMessage(AirmailStatusEvent event) throws CustomException {

		// check the pre- processing criteria
		SaveMailDetailsRequestType req = this.unMarshall(event.getFormattedMessge(), SaveMailDetailsRequestType.class);
		// send the message
		if (!ObjectUtils.isEmpty(req)) {
			SaveMailDetailsResponseType response = camsService.connectorConfig(req);
			// update the status
			if (!ObjectUtils.isEmpty(response) && !"F".equalsIgnoreCase(response.getErrorFlag())) {
				// update the retry count and last updated date time.
				event.setStatus("SENT");
				service.updateCAMSEventLog(event);
			} else {
				service.updateCAMSEventLog(event);
			}
		}

	}

	private void sendMail() throws CustomException{
		
		 EMailEvent emailEvent = new EMailEvent();
		 
		 //get mail address
		String [] mailToAddress= service.getMailListForCAMSAlert();
		String mailBody = service.getTemplateForMail(); 
		
		 emailEvent.setMailToAddress(mailToAddress);
		 emailEvent.setMailSubject("(Auto Generated) Issue with CAMS interface message interrupted" + LocalDateTime.now());
		
		 emailEvent.setMailBody(mailBody);
		 
		 publisher.publish(emailEvent);
	}
}

