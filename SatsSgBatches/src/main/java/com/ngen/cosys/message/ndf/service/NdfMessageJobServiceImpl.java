package com.ngen.cosys.message.ndf.service;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.application.job.NDFMessageJob;
import com.ngen.cosys.events.payload.OutboundNDFMessageEvent;
import com.ngen.cosys.events.producer.OutboundNDFMessageEventProducer;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.ndf.dao.NdfMessageJobDao;
import com.ngen.cosys.message.ndf.model.NdfMessageModel;
@Service
public class NdfMessageJobServiceImpl implements NdfMessageJobService {

	@Autowired
	private NdfMessageJobDao dao;
	@Autowired
	private OutboundNDFMessageEventProducer producer;
	private static final Logger LOGGER = LoggerFactory.getLogger(NDFMessageJob.class);

	@Override
	public List<NdfMessageModel> getNdfMessageDefinition() throws CustomException {
		LOGGER.warn("Inside the NdfMessageJob definition");
		List<NdfMessageModel> messageData = dao.getNdfMessageDefinition();
		if (!CollectionUtils.isEmpty(messageData)) {
			//fetchOiginator address
			String address=dao.fetchOriginatorAddress();
			LOGGER.warn("Inside the NdfMessageJob definition address "+address);
			BigInteger previousDayTotrigger=dao.fetchPreviousDaystoTrigger();
			LOGGER.warn("Inside the NdfMessageJob definition previousDayTotrigger"+previousDayTotrigger);
			for(NdfMessageModel message:messageData) {
				//event triggering to cargo messaging for NDF message
				OutboundNDFMessageEvent event =new  OutboundNDFMessageEvent();
				LOGGER.warn("Inside the NdfMessageJob definition handling area"+message.getHandlingArea());
				event.setHandlingArea(message.getHandlingArea());
				LOGGER.warn("Inside the NdfMessageJob definition telex address"+message.getTelexAddress());
				event.setTelexAdress(message.getTelexAddress());
				event.setPreviousDayToTrigger(previousDayTotrigger);
				event.setOriginatorAddress(address);
				producer.publish(event);
			}
		}
		return messageData;
	}

	

}
