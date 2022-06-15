package com.ngen.cosys.message.resend.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.model.RerouteMessageLog;
import java.math.BigInteger;

/**
 * Incoming Discard Service Implemenatation
 * 
 * @author NIIT Technologies Ltd
 */
public interface RerouteMessageService {
	 /**
	    * GET Discard Messages with status REROUTE from incoming message log 
	    * 
	    * @return
	    * @throws CustomException
	    */
	   List<RerouteMessageLog> getDiscardMessages() throws CustomException;

	    void updateIncomingAndLogOutgoing(BigInteger interfaceIncomingMessageLogId,Object messageTobeSent,String interfacingSystem,String messageType) throws CustomException;
	    

	

}
