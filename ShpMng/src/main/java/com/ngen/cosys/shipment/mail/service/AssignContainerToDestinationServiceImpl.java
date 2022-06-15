/**
 * 
 * AssignContainerToDestinationServiceImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason v0.3 14 April, 2018 NIIT -
 */
package com.ngen.cosys.shipment.mail.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.common.validator.MoveableLocationTypeModel;
import com.ngen.cosys.common.validator.MoveableLocationTypesValidatorImpl;
import com.ngen.cosys.events.payload.AirmailStatusEvent;
import com.ngen.cosys.events.payload.AirmailStatusEventParentModel;
import com.ngen.cosys.events.producer.AirmailStatusEventProducer;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.mail.dao.AssignContainerToDestinationDAO;
import com.ngen.cosys.shipment.mail.model.AssignContainerToDestination;
import com.ngen.cosys.shipment.mail.model.AssignContainerToDestinationDetails;
import com.ngen.cosys.shipment.mail.model.SearchAssignToContainerToDestinationDetails;

/**
 * This class takes care of the assign container to destination services.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Service
@Transactional
public class AssignContainerToDestinationServiceImpl implements AssignContainerToDestinationService {

	@Autowired
	private AssignContainerToDestinationDAO assignContainerToDestinationDAO;

	@Autowired
	AirmailStatusEventProducer producer;
	
	@Autowired
	MoveableLocationTypesValidatorImpl movevableLocation;

	private static final Logger logger = LoggerFactory.getLogger(AssignContainerToDestinationServiceImpl.class);

	@Override
	public AssignContainerToDestination searchAssignContainerDetails(
			SearchAssignToContainerToDestinationDetails request) throws CustomException {
		AssignContainerToDestination assignToDestination;
		List<AssignContainerToDestinationDetails> details;

		assignToDestination = assignContainerToDestinationDAO.searchAssignContainerDetails(request);
		details = assignContainerToDestinationDAO.searchAssignContainerToDestinationDetails(request);
		if (!CollectionUtils.isEmpty(details)) {
			assignToDestination.setDetails(details);
		}
		return assignToDestination;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public AssignContainerToDestination saveAssignContainerDetails(AssignContainerToDestination request)
			throws CustomException {
		AssignContainerToDestination assignToDestination = null;
		List<AssignContainerToDestinationDetails> details;
		details = assignContainerToDestinationDAO.getAssignContainerToDestinationDetails(request);
		int count = assignContainerToDestinationDAO.getAssigneContainerCount(request);
		if (count == 0) {
			assignContainerToDestinationDAO.updateUldMaster(request);
			if (!CollectionUtils.isEmpty(details)) {
				details.forEach(e -> {
					e.setStoreLocation(request.getStoreLocation());
					e.setMailBagNumber(e.getMailBagNumber());
					e.setDestination(request.getDestination());
					e.setTenantId(request.getTenantAirport());
					e.setCreatedBy(request.getCreatedBy());
					e.setCreatedOn(request.getCreatedOn());
				});
				assignContainerToDestinationDAO.updateEaccHouseInfo(details);
				assignToDestination = assignContainerToDestinationDAO.updateHouseInfo(details);
				produceAirmailStatusEvents(details);
			}
		} else {
			throw new CustomException("ACTOD01", "assignContainerToDestination", ErrorType.ERROR);
		}
		return assignToDestination;
	}

	private void produceAirmailStatusEvents(List<AssignContainerToDestinationDetails> details) {
		AirmailStatusEventParentModel eventParentModel = new AirmailStatusEventParentModel();
		List<AirmailStatusEvent> eventList = new ArrayList<>();
		details.forEach(obj -> {
			AirmailStatusEvent event = new AirmailStatusEvent();
			event.setSourceTriggerType("ASSIGNCONTAINERDESTINATION");
			event.setStatus("CREATED");
			
			MoveableLocationTypeModel model = new MoveableLocationTypeModel();
			model.setKey(obj.getStoreLocation());
			
			MoveableLocationTypeModel resp = null;
			try {
				resp = movevableLocation.split(model);
			} catch (CustomException e) {}
			
			event.setStoreLocation(obj.getStoreLocation());
			event.setStoreLocationType(resp.getLocationType());
			
			event.setShipmentNumber(obj.getMailBagNumber().substring(0, 20));
			event.setShipmentId(BigInteger.valueOf(obj.getShipmentId()));
			event.setCarrierCode(obj.getCarrierCode());
			// event.setTransferCarrierTo(obj.getOutgoingCarrier());
			event.setAgentId(obj.getAgentCode());
			event.setMailBag(obj.getMailBagNumber());
			event.setNextDestination(obj.getDestination());

			event.setTenantId(obj.getTenantAirport());
			event.setCreatedOn(obj.getCreatedOn());
			event.setCreatedBy(obj.getCreatedBy());
			logger.warn("Event Data before Producing from assign container destination" + event);
			eventList.add(event);

			logger.warn("Event Data after Producing assign container destination" + event);

		});
		eventParentModel.setAllMessage(eventList);
		producer.publish(eventParentModel);

	}

	@Override
	public AssignContainerToDestination deleteAssignContainerDetails(
			SearchAssignToContainerToDestinationDetails request) throws CustomException {
		AssignContainerToDestination resp = null;
		List<AssignContainerToDestinationDetails> details = assignContainerToDestinationDAO
				.getshipmentInventoryID(request);
		if (!CollectionUtils.isEmpty(details)) {
			details.forEach(e -> e.setShipmentInventoryId(e.getShipmentInventoryId()));
			assignContainerToDestinationDAO.deleteAssignContainerToDestinationDetails(details);
			assignContainerToDestinationDAO.updateLocation(request);
		}
		List<AssignContainerToDestinationDetails> data;
		data = assignContainerToDestinationDAO.getContainerDetails(request);
		if (!CollectionUtils.isEmpty(data)) {
			data.forEach(e -> {
				e.setMailBagNumber(e.getMailBagNumber());
				e.setShipmentId(e.getShipmentId());
			});
			assignContainerToDestinationDAO.updateEaccLocation(data);
			assignContainerToDestinationDAO.updateHouseInfoDestination(data);
		}
		return resp;
	}
}
