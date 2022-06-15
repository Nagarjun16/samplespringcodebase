/**
 * 
 */
package com.ngen.cosys.message.resend.dao;

import java.util.List;

import com.ngen.cosys.events.payload.InterfaceMessageLogModel;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * @author @author NIIT Technologies
 *
 */
public interface RefireFailedEventsDAO {

	/**
	 * @return
	 * @throws CustomException 
	 */
	List<InterfaceMessageLogModel> getfailedEvents() throws CustomException;

	/**
	 * @param eventLogId
	 * @param retryCount
	 */
	void updateRetryCount(InterfaceMessageLogModel interfaceMessageLogModel);

}
