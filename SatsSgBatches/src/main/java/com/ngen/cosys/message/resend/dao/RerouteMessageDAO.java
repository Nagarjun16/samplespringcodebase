/**
 * {@link IncomingFFMLogDAO}
 * 
 * @copyright SATS Singapore 2020-21
 * @author Coforge PTE Ltd
 */
package com.ngen.cosys.message.resend.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.model.RerouteMessageLog;
import com.ngen.cosys.message.resend.model.OutgoingMessageLog;
/**
 * Incoming IncomingDiscardLogDAO
 * 
 * @author Coforge PTE Ltd
 */
public interface RerouteMessageDAO {

	 /**
	    * GET Discard Messages with status REROUTE from incoming message log 
	    * 
	    * @return
	    * @throws CustomException
	    */
	public List<RerouteMessageLog> getDiscardMessages() throws CustomException;
   
	public void updateIncomingMessage(BigInteger interfaceIncomingMessageLogId) throws CustomException;
	
	   public void logOutgoingMessage(OutgoingMessageLog outgoingMessageLog) throws CustomException ;
   
}
