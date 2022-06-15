/**
 * This is an event processor component which processes shipments to customs on document/break down complete events from import functions
 */
package com.ngen.cosys.impbd.events.stream.processor.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ngen.cosys.customs.api.SubmitDataToCustoms;
import com.ngen.cosys.customs.api.enums.CustomsShipmentType;
import com.ngen.cosys.customs.api.model.CustomsShipmentInfo;
import com.ngen.cosys.events.stream.business.processor.BaseBusinessEventStreamProcessor;
import com.ngen.cosys.events.stream.payload.ImpBdPayload;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.events.enums.ImpBdEventTypes;
import com.ngen.cosys.impbd.events.payload.InboundShipmentInfoModel;
import com.ngen.cosys.impbd.events.payload.SingaporeCustomsDataSyncEvent;
import com.ngen.cosys.impbd.events.service.ImpBdEventService;
import com.ngen.cosys.jsonhelper.ConvertJSONToObject;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

@Component(ImpBdEventTypes.Names.SINGAPORE_CUSTOMS_EVENT)
public class SingaporeCustomsDataSyncProcessorImpl implements BaseBusinessEventStreamProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(SingaporeCustomsDataSyncProcessorImpl.class);

	@Autowired
	private ConvertJSONToObject convertJSONToObject;

	@Autowired
	private ImpBdEventService eventService;

	@Autowired
	private SubmitDataToCustoms submitDataToCustoms;

	@Override
	public void process(Message<?> payload) throws JsonGenerationException, JsonMappingException, IOException {
		ImpBdPayload impBdPayload = (ImpBdPayload) payload.getPayload();

		SingaporeCustomsDataSyncEvent event = (SingaporeCustomsDataSyncEvent) convertJSONToObject
				.convertMapToObject(impBdPayload.getPayload(), SingaporeCustomsDataSyncEvent.class);

		try {
			// Get the shipment info list and submit to customs based on flight
			List<InboundShipmentInfoModel> shipments = this.eventService.getInboundCustomsShipmentInfo(event);

			// Check if there are any shipments to submit
			if (!CollectionUtils.isEmpty(shipments)) {

				for (InboundShipmentInfoModel t : shipments) {
					// Submit each shipment
					// Submit the cancelled delivery shipment info
					CustomsShipmentInfo customsShipmentInfo = new CustomsShipmentInfo();
					customsShipmentInfo.setShipmentNumber(t.getShipmentNumber());
					customsShipmentInfo.setFlightKey(event.getFlightKey());
					customsShipmentInfo.setFlightDate(event.getFlightDate());
					customsShipmentInfo.setFlightId(event.getFlightId());
					customsShipmentInfo.setShipmentDate(t.getShipmentDate());
					customsShipmentInfo.setOrigin(t.getOrigin());
					customsShipmentInfo.setDestination(t.getDestination());
					customsShipmentInfo.setCreatedBy(event.getCreatedBy());
					customsShipmentInfo.setModifiedBy(event.getCreatedBy());
					customsShipmentInfo.setCreatedOn(LocalDateTime.now());
					customsShipmentInfo.setModifiedOn(LocalDateTime.now());
					customsShipmentInfo.setFlightType(CustomsShipmentType.Type.IMPORT);
					customsShipmentInfo.setShipmentType(CustomsShipmentType.Type.IMPORT);
					customsShipmentInfo.setEventType(event.getEventType());

					// Submit
					try {
						submitDataToCustoms.submitShipment(customsShipmentInfo);
					} catch (CustomException innerCustomException) {
						LOGGER.error("Exception while submitting customs info for shipment : " + t.getShipmentNumber()
								+ " - " + t.getFlightKey() + " - " + t.getFlightDate(), innerCustomException);
					}
				}

			}
		} catch (CustomException customException) {
			LOGGER.error("Exception while fetching shipments info for customs submission on import", customException);
		}
	}

}