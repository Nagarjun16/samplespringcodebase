package com.ngen.cosys.application.service;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.application.dao.RcsSchedulerDao;
import com.ngen.cosys.application.job.RcsSchedulerJob;
import com.ngen.cosys.application.model.RcsSchedulerDetail;
import com.ngen.cosys.application.model.RcsSchedulerList;
import com.ngen.cosys.events.payload.OutboundShipmentPiecesEqualsToAcceptedPiecesStoreEvent;
import com.ngen.cosys.events.producer.OutboundShipmentPiecesEqualsToAcceptedPiecesStoreEventProducer;
import com.ngen.cosys.framework.exception.CustomException;



@Service
public class RcsSchedulerServiceImpl implements RcsSchedulerService{
	
	@Autowired
	private OutboundShipmentPiecesEqualsToAcceptedPiecesStoreEventProducer shipmentPiecesEqualsToPieceStoreEventProducer;
	
	@Autowired
	private RcsSchedulerDao dao;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RcsSchedulerServiceImpl.class);
	   
	@Override
	public void getRcsTriggerFlightList() throws CustomException {
		
	      LOGGER.warn("Inside the getRcsTriggerFlightList() method ");
	      
		RcsSchedulerList list= new RcsSchedulerList();
	      LOGGER.warn("before dao.getList()" , list);
		list.setGetRcsSchedulerDetails(dao.getList());
	      LOGGER.warn("after dao.getList()" , list);
		BigInteger IsEAwbShipment = null;
		Boolean rcsEawbTriggerStatus = null;
		
		if(!list.getGetRcsSchedulerDetails().isEmpty()) {
			
			for(RcsSchedulerDetail schedulerlist :list.getGetRcsSchedulerDetails()) {
				rcsEawbTriggerStatus = true;
				IsEAwbShipment = dao.getIsShipmentEawb(schedulerlist.getShipmentNumber());
			      LOGGER.warn("value of IsEAwbShipment",IsEAwbShipment);
				if (IsEAwbShipment.intValue() > 0) {
					rcsEawbTriggerStatus = false;
				}
			
			
				OutboundShipmentPiecesEqualsToAcceptedPiecesStoreEvent interEvent = new OutboundShipmentPiecesEqualsToAcceptedPiecesStoreEvent();
				interEvent.setShipmentId(schedulerlist.getShipmentId());
				interEvent.setCreatedOn(LocalDateTime.now());
				interEvent.setCreatedBy("BatchJob");
				interEvent.setConfirmedAt(LocalDateTime.now());
				interEvent.setConfirmedBy("BatchJob");
				interEvent.setFirstBookingFlightId(schedulerlist.getFirstBookingFlightId());
				interEvent.setPieces(schedulerlist.getPieces());
				interEvent.setWeight(schedulerlist.getWeight());
				interEvent.setLastModifiedBy("BatchJob");
				interEvent.setLastModifiedOn(LocalDateTime.now());
				interEvent.setRcsEawbStatus(rcsEawbTriggerStatus);
				interEvent.setRcsRACShcCheck(1);
				interEvent.setStatus("NEW");
			      LOGGER.warn("before publish");
				shipmentPiecesEqualsToPieceStoreEventProducer.publish(interEvent);
			      LOGGER.warn("after publish");
				
			}
				
		}
		
	}

}
