package com.ngen.cosys.application.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.application.model.AutoFreightoutInventoryModel;
import com.ngen.cosys.application.model.AutoFreightoutModel;
import com.ngen.cosys.application.service.AutoFreightoutService;
import com.ngen.cosys.events.enums.EventStatus;
import com.ngen.cosys.events.enums.EventTypes;
import com.ngen.cosys.events.payload.InboundShipmentDeliveredStoreEvent;
import com.ngen.cosys.events.producer.InboundShipmentDeliveredStoreEventProducer;
import com.ngen.cosys.framework.exception.CustomException;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class AutoFreightOutJobController {

	@Autowired
	private AutoFreightoutService service;
	

	@Autowired
	InboundShipmentDeliveredStoreEventProducer producer;

	@ApiOperation("API for Testing Auto Freightout")
	@RequestMapping(value = "/api/satssgbatches/autoFreightout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void autoFreightout() throws CustomException {

		// Get the list of shipments
		List<AutoFreightoutModel> shipments = this.service.getShipments();

		for (AutoFreightoutModel freightoutModel : shipments) {

			List<AutoFreightoutInventoryModel> inventoryList = this.service.getInventoryDetails(freightoutModel);

			for (AutoFreightoutInventoryModel inventoryModel : inventoryList) {
				
				inventoryModel.setDeliveryId(freightoutModel.getDeliveryId());
				
				this.service.freightoutToInventory(inventoryModel);
				
			}
			
			InboundShipmentDeliveredStoreEvent event = new InboundShipmentDeliveredStoreEvent();
			event.setCreatedBy(freightoutModel.getCreatedBy());
			event.setCreatedOn(LocalDateTime.now());
			event.setDeliveredAt(LocalDateTime.now());
			event.setDeliveredBy(freightoutModel.getCreatedBy());
			event.setDoNumber(freightoutModel.getDeliveryOrderNo());
			event.setStatus(EventStatus.NEW.getStatus());
			event.setShipmentNumber(freightoutModel.getShipmentNumber());
			event.setShipmentDate(freightoutModel.getShipmentDate());
			event.setShipmentId(freightoutModel.getShipmentId());
			event.setShipmentType(freightoutModel.getShipmentType());
			event.setCarrier(freightoutModel.getCarrierCode());
			event.setFunction("Issue DO");
			event.setEventName(EventTypes.Names.INBOUND_SHIPMENT_DELIVERED_EVENT);
		   
			this.producer.publish(event);

		}
		
	
		

	}
}
