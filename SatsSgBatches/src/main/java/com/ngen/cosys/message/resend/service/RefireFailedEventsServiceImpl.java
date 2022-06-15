/**
 * 
 */
package com.ngen.cosys.message.resend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.events.payload.InterfaceMessageLogModel;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.dao.RefireFailedEventsDAO;

/**
 * @author NIIT Technologies ltd
 *
 */
@Service
public class RefireFailedEventsServiceImpl implements RefireFailedEventsService {

	@Autowired
	RefireFailedEventsDAO refireFailedEventsDAO;
	@Override
	public List<InterfaceMessageLogModel> getfailedEvents() throws CustomException {
		// TODO Auto-generated method stub
		return refireFailedEventsDAO.getfailedEvents();
	}
	@Override
	public void updateRetryCount(InterfaceMessageLogModel interfaceMessageLogModel) {
		// TODO Auto-generated method stub
		refireFailedEventsDAO.updateRetryCount(interfaceMessageLogModel);
		
	}

}
