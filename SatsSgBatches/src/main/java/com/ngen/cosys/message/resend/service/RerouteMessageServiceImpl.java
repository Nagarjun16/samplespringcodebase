package com.ngen.cosys.message.resend.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.common.MessageResendConstants;
import com.ngen.cosys.message.resend.dao.RerouteMessageDAO;
import com.ngen.cosys.message.resend.model.RerouteMessageLog;
import com.ngen.cosys.message.resend.model.OutgoingMessageLog;


/**
 * Reroute Message Service Implementation
 * 
 * @author Coforge
 */

@Service
public class RerouteMessageServiceImpl implements RerouteMessageService{
	 @Autowired
	 RerouteMessageDAO incomingDiscardLogDAO;
	 /**
	    * GET Discard Messages with status REROUTE from incoming message log 
	    * 
	    * @return
	    * @throws CustomException
	    */
	 @Override
	   public List<RerouteMessageLog> getDiscardMessages() throws CustomException{
		   List<RerouteMessageLog> discardMessages = incomingDiscardLogDAO.getDiscardMessages();
		   return discardMessages;
		   
	   }
	 
	 /**
	    * Update Incoming log as DIVERTED and insert Outgoing Log with message sent to ARINC
	    * 
	    * @return
	    * @throws CustomException
	    */
	 @Override
	 @Transactional(readOnly = false, rollbackFor = Throwable.class)
	 public void updateIncomingAndLogOutgoing(BigInteger interfaceIncomingMessageLogId,Object messageTobeSent,String interfacingSystem,String messageType) throws CustomException
	   {
		 updateIncoming(interfaceIncomingMessageLogId);		 
		 logOutgoingMessage(messageTobeSent,interfacingSystem,messageType);	     
	   }
	 
	   public void updateIncoming(BigInteger interfaceIncomingMessageLogId) throws CustomException
	   {	 
		
	      incomingDiscardLogDAO.updateIncomingMessage(interfaceIncomingMessageLogId);
	   }
	 
	 
	
	   public void logOutgoingMessage(Object messageTobeSent,String interfacingSystem,String messageType) throws CustomException {
	      //
	      OutgoingMessageLog outgoingMessage = this.getOutgoingMessageLog(messageTobeSent,interfacingSystem,messageType);
	      incomingDiscardLogDAO.logOutgoingMessage(outgoingMessage);
	     
	   }
	 
	 /**
	    * @param payload
	    * @param requestType
	    * @param interfaceSystem
	    * @param logicalName
	    * @return
	    */
	   private OutgoingMessageLog getOutgoingMessageLog(Object messageTobeSent,String interfaceSystem,String messageType) {
	      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
	      //
	      outgoingMessage.setMedium(interfaceSystem);	     
	      outgoingMessage.setInterfacingSystem(interfaceSystem);
	      outgoingMessage.setSenderOriginAddress(null);
	      if(StringUtils.isEmpty(messageType) || (!StringUtils.isEmpty(messageType)  && messageType.length()>4)  )
	      {
	    	  outgoingMessage.setMessageType("TLX"); 
	      }	     
	      else
	      {
	    	  outgoingMessage.setMessageType(messageType);
	      }
	     
	      outgoingMessage.setSubMessageType(null);
	      outgoingMessage.setCarrierCode(null);
	      outgoingMessage.setFlightNumber(null);
	      outgoingMessage.setFlightOriginDate(null);
	      outgoingMessage.setShipmentNumber(null);
	      outgoingMessage.setShipmentDate(null);
	      outgoingMessage.setRequestedOn(LocalDateTime.now());
	      outgoingMessage.setSentOn(LocalDateTime.now());
	      outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
	      outgoingMessage.setMessage(messageTobeSent.toString());
	      outgoingMessage.setVersionNo(1);
	      outgoingMessage.setSequenceNo(1);
	      outgoingMessage.setMessageContentEndIndicator(null);
	      outgoingMessage.setStatus(MessageResendConstants.DIVERTED);
	      //
	      return outgoingMessage;
	   }
	     
	 

	   
		



}
