package com.ngen.cosys.message.resend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.AirmailStatus.DAO.AirmailStatusDAO;
import com.ngen.cosys.events.payload.AirmailStatusEvent;
import com.ngen.cosys.events.payload.CAMSModel;
import com.ngen.cosys.framework.exception.CustomException;

@Service
public class ResendCAMSMessagesServiceImpl implements ResendCAMSMessagesService {

	@Autowired
	AirmailStatusDAO dao;
	
	
	@Override
	public List<AirmailStatusEvent> fetchFailedCAMSMessages() throws CustomException {
		return dao.fetchFailCAMSMessages();
	}


	@Override
	public void updateCAMSEventLog(AirmailStatusEvent event) throws CustomException {
		dao.updateCAMSEventLog(event);	
	}


	@Override
	public String[] getMailListForCAMSAlert() throws CustomException {
		List<String> list = dao.getMailListForCAMSAlert();
		return  CollectionUtils.isEmpty(list) ? null : list.toArray(new String[list.size()]);
	}


	@Override
	public String getTemplateForMail() throws CustomException {
		return dao.getTemplateForMail();
	}


	@Override
	public CAMSModel getCAMSInterfaceConfigurations() throws CustomException {
		// TODO Auto-generated method stub
		return dao.getCAMSInterfaceConfigurations();
	}

}
