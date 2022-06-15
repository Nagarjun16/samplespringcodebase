/**
 * Service component implementation class which has methods exposed for sending FSU messages that were missed out to send
 */
package com.ngen.cosys.message.resend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.events.enums.EventStatus;
import com.ngen.cosys.events.enums.EventTypes;
import com.ngen.cosys.events.payload.OutboundShipmentFlightCompletedStoreEvent;
import com.ngen.cosys.events.payload.OutboundShipmentManifestedStoreEvent;
import com.ngen.cosys.events.payload.OutboundShipmentPiecesEqualsToAcceptedPiecesStoreEvent;
import com.ngen.cosys.events.producer.OutboundShipmentFlightCompletedStoreEventProducer;
import com.ngen.cosys.events.producer.OutboundShipmentManifestedStoreEventProducer;
import com.ngen.cosys.events.producer.OutboundShipmentPiecesEqualsToAcceptedPiecesStoreEventProducer;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.dao.OutgoingShipmentsMissingFSUMessageSendDAO;
import com.ngen.cosys.message.resend.model.OutgoingShipmentsMissingFSURCSMessageSendModel;

@Service
@Transactional
public class OutgoingShipmentsMissingFSUMessageSendServiceImpl
		implements OutgoingShipmentsMissingFSUMessageSendService {

	// Message related constants
	private static final String DEFAULT_FUNCTION_NAME = "Resend FSU Message";
	private static final String DEFAULT_USER = "RESENDBATCH";
	private static final String FSU = "FSU";
	private static final String RCS = "RCS";
	private static final String ECC_ACCEPTANCE = "ECC_ACCEPTANCE";

	@Autowired
	private OutgoingShipmentsMissingFSUMessageSendDAO dao;

	@Autowired
	private OutboundShipmentFlightCompletedStoreEventProducer outboundShipmentFlightCompletedStoreEventProducer;

	@Autowired
	private OutboundShipmentManifestedStoreEventProducer outboundShipmentManifestedStoreEventProducer;

	@Autowired
	private OutboundShipmentPiecesEqualsToAcceptedPiecesStoreEventProducer outboundShipmentPiecesEqualsToAcceptedPiecesStoreEventProducer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.message.resend.service.
	 * OutgoingShipmentsMissingFSUMessageSendService#refireDEPMissingShipments()
	 */
	@Override
	public void refireDEPMissingShipments() throws CustomException {
		// Get the shipment list
		List<OutboundShipmentFlightCompletedStoreEvent> shipments = this.dao.getFlightCompletedShipments();

		// Produce the event
		if (!CollectionUtils.isEmpty(shipments)) {
			for (OutboundShipmentFlightCompletedStoreEvent event : shipments) {
				event.setStatus(EventStatus.NEW.getStatus());
				event.setCompletedBy(DEFAULT_USER);
				event.setCompletedAt(LocalDateTime.now());
				event.setCreatedBy(DEFAULT_USER);
				event.setCreatedOn(LocalDateTime.now());
				event.setFunction(DEFAULT_FUNCTION_NAME);
				event.setEventName(EventTypes.Names.OUTBOUND_SHIPMENT_FLIGHT_COMPLETED_EVENT);
				this.outboundShipmentFlightCompletedStoreEventProducer.publish(event);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.message.resend.service.
	 * OutgoingShipmentsMissingFSUMessageSendService#refireMANMissingShipments()
	 */
	@Override
	public void refireMANMissingShipments() throws CustomException {
		// Get the shipment list
		List<OutboundShipmentManifestedStoreEvent> shipments = this.dao.getManifestCompleteShipments();

		// Produce the event
		if (!CollectionUtils.isEmpty(shipments)) {
			for (OutboundShipmentManifestedStoreEvent event : shipments) {
				event.setStatus(EventStatus.NEW.getStatus());
				event.setCreatedBy(DEFAULT_USER);
				event.setCreatedOn(LocalDateTime.now());
				event.setMessageType(null);
				event.setSubMessageType(null);
				event.setTriggerPoint("MANIFEST");
				event.setFunction(DEFAULT_FUNCTION_NAME);
				event.setEventName(EventTypes.Names.OUTBOUND_SHIPMENT_MANIFESTED_EVENT);
				this.outboundShipmentManifestedStoreEventProducer.publish(event);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.message.resend.service.
	 * OutgoingShipmentsMissingFSUMessageSendService#refireRCSMissingShipments()
	 */
	@Override
	public void refireRCSMissingShipments() throws CustomException {
		// Get the shipment list
		List<OutgoingShipmentsMissingFSURCSMessageSendModel> shipments = this.dao.getAcceptanceFinalizeShipmens();

		// Produce the event
		if (!CollectionUtils.isEmpty(shipments)) {
			for (OutgoingShipmentsMissingFSURCSMessageSendModel s : shipments) {

				// Fuse variable to decide whether RCS can be triggered OR not
				boolean triggerRcs = false;

				// ECC Acceptance Type
				if (ECC_ACCEPTANCE.equalsIgnoreCase(s.getAcceptanceType())
						&& (!ObjectUtils.isEmpty(s.getCargoPhysicallyAccepted()) && s.getCargoPhysicallyAccepted())
						&& ((s.getDepTime().intValue() > 30 && s.getPiece().compareTo(s.getFwbPieces()) == 0
								&& s.getWeight().compareTo(s.getFwbWeight()) == 0)
								|| s.getDepTime().intValue() <= 30)) {
					// FWB pieces /weight matches the physical acceptance pieces /weight then
					// trigger RCS else do not trigger RCS
					// If revised FWB comes then if pieces /weight matches the physical acceptance
					// pieces /weight then trigger RCS else do not trigger RCS
					// 30 minutes before STD system should auto trigger RCS if not sent earlier even
					// if FWBpieces/ weight do not match with physical pieces/weight
					triggerRcs = true;

				} else {

					// Check EAWB triggered and pieces/weight matches FWB
					boolean isEawbAndPieceWeightMatches = false;
					if (s.getEawb() && (s.getPiece().compareTo(s.getFwbPieces()) == 0
							&& s.getWeight().compareTo(s.getFwbWeight()) == 0)) {
						isEawbAndPieceWeightMatches = true;
					}

					// Check Weight Tolerance Triggered and No Failure Exists OR no tolerance check
					// exists
					boolean weightToleranceExistsAndAcknowledged = true;
					if (s.getWeightToleranceCheckTriggered() && !s.getWeightToleranceIssueClosed()) {
						weightToleranceExistsAndAcknowledged = false;
					}

					// FWB pieces /weight matches the physical acceptance pieces /weight then
					// trigger RCS else do not trigger RCS. Also do not trigger if weight tolerance
					// failure exists

					// If FWB not Required for AWB for Carrier/Destination and Trigger RCS if no
					// weight tolerance
					if ((isEawbAndPieceWeightMatches && weightToleranceExistsAndAcknowledged)
							|| (!s.getEawb() && weightToleranceExistsAndAcknowledged)) {
						triggerRcs = true;
					}
				}

				// If all conditions meets then trigger message based on trigger RCS status
				if (triggerRcs) {
					OutboundShipmentPiecesEqualsToAcceptedPiecesStoreEvent event = new OutboundShipmentPiecesEqualsToAcceptedPiecesStoreEvent();
					event.setShipmentId(s.getShipmentId());
					event.setPieces(s.getPiece());
					event.setWeight(s.getWeight());
					event.setRcsEawbStatus(s.getEawb().booleanValue());
					event.setRcsRACShcCheck(s.getRac().booleanValue() ? 1 : 0);
					event.setMessageType(FSU);
					event.setSubMessageType(RCS);
					event.setCreatedOn(LocalDateTime.now());
					event.setCreatedBy(DEFAULT_USER);
					event.setConfirmedAt(s.getCreatedOn());
					event.setConfirmedBy(s.getCreatedUserCode());
					event.setFirstBookingFlightId(null);
					event.setLastModifiedBy(DEFAULT_USER);
					event.setLastModifiedOn(LocalDateTime.now());
					event.setFunction(DEFAULT_FUNCTION_NAME);
					event.setEventName(EventTypes.Names.OUTBOUND_SHIPMENT_PIECES_EQUALS_TO_ACCEPTED_PIECES_EVENT);
					event.setStatus(EventStatus.Type.NEW);
					outboundShipmentPiecesEqualsToAcceptedPiecesStoreEventProducer.publish(event);
				}
			}
		}
	}

}