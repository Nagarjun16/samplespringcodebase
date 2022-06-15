package com.ngen.cosys.message.resend.service;

import java.util.List;

import com.ngen.cosys.events.payload.AirmailStatusEvent;
import com.ngen.cosys.events.payload.CAMSModel;
import com.ngen.cosys.framework.exception.CustomException;

public interface ResendCAMSMessagesService {

	List<AirmailStatusEvent> fetchFailedCAMSMessages() throws CustomException;

	void updateCAMSEventLog(AirmailStatusEvent event) throws CustomException;

	String[] getMailListForCAMSAlert() throws CustomException;

	String getTemplateForMail()throws CustomException;

	CAMSModel getCAMSInterfaceConfigurations() throws CustomException ;
}
