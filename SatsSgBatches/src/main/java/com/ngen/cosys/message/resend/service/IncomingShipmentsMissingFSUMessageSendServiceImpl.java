/**
 * Service implementation class for sending missing FSU messages on import shipments
 */
package com.ngen.cosys.message.resend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.events.enums.EventStatus;
import com.ngen.cosys.events.enums.EventTypes;
import com.ngen.cosys.events.payload.InboundShipmentBreakDownCompleteStoreEvent;
import com.ngen.cosys.events.payload.InboundShipmentDeliveredStoreEvent;
import com.ngen.cosys.events.payload.InboundShipmentDocumentReleaseStoreEvent;
import com.ngen.cosys.events.producer.InboundShipmentBreakDownCompleteStoreEventProducer;
import com.ngen.cosys.events.producer.InboundShipmentDeliveredStoreEventProducer;
import com.ngen.cosys.events.producer.InboundShipmentDocumentReleaseStoreEventProducer;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.dao.IncomingShipmentsMissingFSUMessageSendDAO;

@Service
@Transactional
public class IncomingShipmentsMissingFSUMessageSendServiceImpl
		implements IncomingShipmentsMissingFSUMessageSendService {

	// Message related constants
	private static final String DEFAULT_FUNCTION_NAME = "Resend FSU Message";
	private static final String DEFAULT_USER = "RESENDBATCH";

	@Autowired
	private IncomingShipmentsMissingFSUMessageSendDAO dao;

	// RCF/NFD
	@Autowired
	private InboundShipmentBreakDownCompleteStoreEventProducer inboundShipmentBreakDownCompleteStoreEventProducer;

	// AWD
	@Autowired
	private InboundShipmentDocumentReleaseStoreEventProducer inboundShipmentDocumentReleaseStoreEventProducer;

	// DLV
	@Autowired
	private InboundShipmentDeliveredStoreEventProducer inboundShipmentDeliveredStoreEventProducer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.message.resend.service.
	 * IncomingShipmentsMissingFSUMessageSendService#refireRCFNFDMissingShipments()
	 */
	@Override
	public void refireRCFNFDMissingShipments() throws CustomException {
		// Get the shipment list
		List<InboundShipmentBreakDownCompleteStoreEvent> shipments = this.dao.getBreakDownCompleteShipments();

		// Produce the event
		if (!CollectionUtils.isEmpty(shipments)) {
			for (InboundShipmentBreakDownCompleteStoreEvent event : shipments) {
				event.setStatus(EventStatus.NEW.getStatus());
				event.setCompletedAt(LocalDateTime.now());
				event.setCompletedBy(DEFAULT_USER);
				event.setCreatedOn(LocalDateTime.now());
				event.setCreatedBy(DEFAULT_USER);
				event.setFunction(DEFAULT_FUNCTION_NAME);
				event.setEventName(EventTypes.Names.INBOUND_SHIPMENT_BREAK_DOWN_COMPLETE_EVENT);
				// Publish
				inboundShipmentBreakDownCompleteStoreEventProducer.publish(event);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.message.resend.service.
	 * IncomingShipmentsMissingFSUMessageSendService#refireAWDMissingShipments()
	 */
	@Override
	public void refireAWDMissingShipments() throws CustomException {
		// Get the shipment list
		List<InboundShipmentDocumentReleaseStoreEvent> shipments = this.dao.getDocumentReleasedShipments();

		// Produce the event
		if (!CollectionUtils.isEmpty(shipments)) {
			for (InboundShipmentDocumentReleaseStoreEvent event : shipments) {
				event.setStatus(EventStatus.NEW.getStatus());
				event.setReleasedAt(LocalDateTime.now());
				event.setReleasedBy(DEFAULT_USER);
				event.setCreatedBy(DEFAULT_USER);
				event.setCreatedOn(LocalDateTime.now());
				event.setFunction(DEFAULT_FUNCTION_NAME);
				event.setEventName(EventTypes.Names.INBOUND_SHIPMENT_DOCUMENT_RELEASE_EVENT);
				// Publish the event
				inboundShipmentDocumentReleaseStoreEventProducer.publish(event);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.message.resend.service.
	 * IncomingShipmentsMissingFSUMessageSendService#refireDLVMissingShipments()
	 */
	@Override
	public void refireDLVMissingShipments() throws CustomException {
		// Get the shipment list
		List<InboundShipmentDeliveredStoreEvent> shipments = this.dao.getDeliveredShipments();

		// Produce the event
		if (!CollectionUtils.isEmpty(shipments)) {
			for (InboundShipmentDeliveredStoreEvent event : shipments) {
				event.setCreatedBy(DEFAULT_USER);
				event.setCreatedOn(LocalDateTime.now());
				event.setStatus(EventStatus.NEW.getStatus());
				event.setFunction(DEFAULT_FUNCTION_NAME);
				event.setEventName(EventTypes.Names.INBOUND_SHIPMENT_DELIVERED_EVENT);
				// Publish the event
				this.inboundShipmentDeliveredStoreEventProducer.publish(event);
			}
		}
	}

}