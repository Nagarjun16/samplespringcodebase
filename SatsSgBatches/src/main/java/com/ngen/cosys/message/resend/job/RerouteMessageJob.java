/**
 * {@link IncomingFFMMessageResendJob}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.job;

import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.CRLF;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.common.MessageResendConstants;
import com.ngen.cosys.message.resend.model.RerouteMessageLog;
import com.ngen.cosys.message.resend.service.RerouteMessageService;
import com.ngen.cosys.message.resend.util.MessageTypeUtils;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.scheduler.esb.connector.ConnectorPublisher;
import com.ngen.cosys.scheduler.esb.connector.ConnectorUtils;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

/**
 * This is a JOb for DIVERTING/ Reroute Message to ARINC which have come from DIVERTNONEDIFLY/DIVERTEDIFLY
 * for sequence message processing
 * 
 * @author NIIT Technologies Ltd
 */
@Component(value = MessageResendConstants.INCOMING_MESSAGE_DISCARD)
public class RerouteMessageJob extends AbstractCronJob {

   private static final Logger LOGGER = LoggerFactory.getLogger(RerouteMessageJob.class);
   @Autowired
   private ConnectorPublisher publisher;  

   @Autowired
   RerouteMessageService incomingDiscardLogService;
  
   
   private static final String ARINCCHANNEL ="ARINC";

   /**
    * Scheduler for sending REROUTE(DIVERTED) messages to ESB(ARINC channel) and logging
    * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.JobExecutionContext)
    */
   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      try {
         LOGGER.info("Incoming Discard message process Initiated {}");         
         List<RerouteMessageLog> discardMessages = incomingDiscardLogService.getDiscardMessages();
         sendToARINCAndLog(discardMessages);
         
         
      } catch (Exception ex) {
         LOGGER.error("Incoming FFM Message Sequence Process exception occurred - {}", ex);
      }
   }

	
	
	private void sendToARINCAndLog(List<RerouteMessageLog> discardMessages) throws IOException, CustomException {
		BigInteger messageId = null;      
        boolean loggerEnabled = false;
        Map<String, String> payloadHeaders = ConnectorUtils.getPayloadHeaders(messageId, loggerEnabled, null,TenantContext.getTenantId());
		for(RerouteMessageLog discardMessage: discardMessages)
		{
			 try {
					String messageWithNoEnvolpe ="";
					StringBuilder sb = new StringBuilder();
					List<String> messageLines = MessageTypeUtils.getMessageLineData(discardMessage.getMessage());
					List<String> messageLinesNoEvelope = MessageTypeUtils.parseMessageEnvelope(messageLines, false,null,true);
				      // Appends characters one by one
				      for (String message : messageLinesNoEvelope) {
				          sb.append(message);
				          sb.append(CRLF);
				          messageWithNoEnvolpe = sb.toString();
				      }
				      byte[] byteMsg =  MessageTypeUtils.generateHeaderFormat2(messageWithNoEnvolpe,true,messageLines);	         	
				      String messageTobeSent = new String(byteMsg);	      
				      publisher.sendJobDataToConnector(messageTobeSent,ARINCCHANNEL, MediaType.APPLICATION_JSON, payloadHeaders); 
				      String messageType = MessageTypeUtils.getMessageType(messageLinesNoEvelope);
				     
				    	  incomingDiscardLogService.updateIncomingAndLogOutgoing(discardMessage.getInterfaceIncomingMessageLogId(),messageTobeSent, ARINCCHANNEL,messageType);
				      }
				      catch(Exception e)
				      {
				    	  
				      }	     
		      

		}
		
		
	}
	
	
   
   
}
