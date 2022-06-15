/**
 * 
 */
package com.ngen.cosys.message.resend.service;

import java.util.List;

import com.ngen.cosys.events.payload.InterfaceMessageLogModel;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * @author  NIIT Technologies ltd
 *
 */
public interface RefireFailedEventsService {

	/**
	 * @return 
	 * @throws CustomException 
	 * 
	 */
	List<InterfaceMessageLogModel> getfailedEvents() throws CustomException;

	/**
	 * @param eventLogId
	 * @param retryCount
	 */
	void updateRetryCount(InterfaceMessageLogModel interfaceMessageLogModel);

}
