/**
 * This is a rest service class 
 */
package com.ngen.cosys.aed.dummy.invoker;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.events.payload.OutboundFlightCompleteStoreEvent;
import com.ngen.cosys.events.payload.OutboundShipmentPiecesEqualsToAcceptedPiecesStoreEvent;
import com.ngen.cosys.events.payload.OutboundShipmentRcarScreenScanInfoStoreEvent;
import com.ngen.cosys.events.payload.OutboundShipmentStartCargoAcceptanceStoreEvent;
import com.ngen.cosys.events.producer.OutboundFlightCompleteStoreEventProducer;
import com.ngen.cosys.events.producer.OutboundShipmentPiecesEqualsToAcceptedPiecesStoreEventProducer;
import com.ngen.cosys.events.producer.OutboundShipmentRcarScreenScanInfoStoreEventProducer;
import com.ngen.cosys.events.producer.OutboundShipmentStartCargoAcceptanceStoreEventProducer;
import com.ngen.cosys.framework.model.BaseResponse;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation(path = "/api/aed/outgoing")
public class RaiseOutgoingMessageAEDController {

	@Autowired
	BeanFactory beanFactory;

	@Autowired
	OutboundShipmentStartCargoAcceptanceStoreEventProducer producer;

	@Autowired
	OutboundShipmentPiecesEqualsToAcceptedPiecesStoreEventProducer producerPieces;

	@Autowired
	OutboundFlightCompleteStoreEventProducer flightComplete;

	@Autowired
	OutboundShipmentRcarScreenScanInfoStoreEventProducer rcarscreenInfo;

	@ApiOperation("API Method for raising an application event for producing outgoing message")
	@PostRequest(path = "/dummy/raise/event", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<String> raiseEventforStartAcceptance(
			@RequestBody OutboundShipmentStartCargoAcceptanceStoreEvent event) {

		@SuppressWarnings("unchecked")
		BaseResponse<String> response = beanFactory.getBean(BaseResponse.class);

		event.setShipmentNumber(event.getShipmentNumber());
		event.setShipmentDate(LocalDate.now());
		event.setPieces(event.getPieces());
		event.setWeight(event.getWeight());
		event.setFlightId(event.getFlightId());
		event.setCreatedBy("AED");
		event.setCreatedOn(LocalDateTime.now());

		// Publish the event
		producer.publish(event);

		return response;

	}

	@ApiOperation("API Method for raising an application event for producing outgoing message fot autoweight pieces")
	@PostRequest(path = "/dummy/raise/event/autowaight", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<String> raisEventForFinalizedAutoWaight(
			@RequestBody OutboundShipmentPiecesEqualsToAcceptedPiecesStoreEvent event) {

		@SuppressWarnings("unchecked")
		BaseResponse<String> response = beanFactory.getBean(BaseResponse.class);

		event.setShipmentId(event.getShipmentId());
		event.setFirstBookingFlightId(event.getFirstBookingFlightId());
		event.setPieces(event.getPieces());
		event.setWeight(event.getWeight());
		event.setStatus(event.getStatus());
		event.setConfirmedAt(LocalDateTime.now());
		event.setConfirmedBy("system");
		event.setCreatedBy("system");
		event.setCreatedOn(LocalDateTime.now());
		event.setLastModifiedBy("system");
		event.setLastModifiedOn(LocalDateTime.now());
		// Publish the event
		producerPieces.publish(event);

		return response;

	}

	@ApiOperation("API Method for raising an application event for producing outgoing message for flightcomplte")
	@PostRequest(path = "/dummy/raise/event/flightcomplted", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<String> raiseEventForFlightComplete(
			@RequestBody OutboundFlightCompleteStoreEvent event) {
		@SuppressWarnings("unchecked")
		BaseResponse<String> response = beanFactory.getBean(BaseResponse.class);

	
		flightComplete.publish(event);

		return response;

	}

	@ApiOperation("API Method for raising an application event for producing outgoing message for Rcar Screen Info")
	@PostRequest(path = "/dummy/raise/event/rcarScreen", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<String> raiseEventForRcarSacnInfo(
			@RequestBody OutboundShipmentRcarScreenScanInfoStoreEvent event) {

		@SuppressWarnings("unchecked")
		BaseResponse<String> response = beanFactory.getBean(BaseResponse.class);

		event.setShipmentId(event.getShipmentId());
		event.setFlightId(event.getFlightId());
		event.setPieces(event.getPieces());
		event.setWeight(event.getWeight());
		event.setScreeningReason("Informed");
		event.setCreatedBy("system");
		event.setCreatedOn(LocalDateTime.now());
		event.setLastModifiedBy("system");
		event.setLastModifiedOn(LocalDateTime.now());
		// Publish the event
		rcarscreenInfo.publish(event);

		return response;

	}

}
