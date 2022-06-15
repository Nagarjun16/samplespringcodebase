package com.ngen.cosys.impbd.vctinformation.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.events.enums.EventTypes;
import com.ngen.cosys.events.payload.ExportCargoVehicleDockMessageEvent;
import com.ngen.cosys.events.payload.ImportCargoPickupScheduleStoreEvent;
import com.ngen.cosys.events.producer.ExportCargoVehicleDockMessageEventProducer;
import com.ngen.cosys.events.producer.ImportCargoPickupScheduleStoreEventProducer;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.vctinformation.model.VCTInformation;
import com.ngen.cosys.impbd.vctinformation.service.VctInformationService;

@NgenCosysAppInfraAnnotation
public class VctInformationController {

	@Autowired
	UtilitiesModelConfiguration utility;

	@Autowired
	VctInformationService vctInformationService;

	@Autowired
	ImportCargoPickupScheduleStoreEventProducer producer;

	@Autowired
	ExportCargoVehicleDockMessageEventProducer vctEXProducer;

	
	
	private static final String DOCK_IN = "DOCKIN";
	private static final String DOCK_OUT = "DOCKOUT";

	@RequestMapping(value = "/api/impbd/vctinformation/getvctinformationList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<VCTInformation> searchDocuments(@Validated @RequestBody VCTInformation vctInformationModel)
			throws CustomException {
		// @SuppressWarnings("unchecked")
		@SuppressWarnings("unchecked")
		BaseResponse<VCTInformation> vctInformationRs = utility.getBaseResponseInstance();
		vctInformationModel = vctInformationService.fetch(vctInformationModel);
		vctInformationRs.setData(vctInformationModel);
		return vctInformationRs;
	}

	
	@RequestMapping(value = "/api/impbd/vctinformation/savevctinformationList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<VCTInformation> add(@RequestBody VCTInformation vctinformation) throws CustomException {
		BaseResponse<VCTInformation> vctinformationresponse = utility.getBaseResponseInstance();
		vctinformation = vctInformationService.save(vctinformation);
		vctinformationresponse.setData(vctinformation);
		if(vctinformation.getType().equalsIgnoreCase("IMPORT")) {
		// Publish Dock/DockOut Messages
		ImportCargoPickupScheduleStoreEvent importCargoPickupScheduleStoreEvent = new ImportCargoPickupScheduleStoreEvent();
		importCargoPickupScheduleStoreEvent.setScheduleNumber(vctinformation.getVctNumber());
		importCargoPickupScheduleStoreEvent.setEventName(EventTypes.Names.IMPORT_CARGO_PICKUP_SCHEDULE);
		if (vctinformation.getVctIn()) {
			importCargoPickupScheduleStoreEvent.setAction(DOCK_IN);
		} else if (vctinformation.getVctOut()) {
			importCargoPickupScheduleStoreEvent.setAction(DOCK_OUT);
		}
		importCargoPickupScheduleStoreEvent.setFunction("VCT Information");
		importCargoPickupScheduleStoreEvent.setCreatedBy(vctinformation.getCreatedBy());
		importCargoPickupScheduleStoreEvent.setCreatedOn(vctinformation.getCreatedOn());

		producer.publish(importCargoPickupScheduleStoreEvent);
		}
		else {

		// Publish Dock/DockOut Messages
		ExportCargoVehicleDockMessageEvent exportCargoVehicleDockInMessageEvent = new ExportCargoVehicleDockMessageEvent();
		exportCargoVehicleDockInMessageEvent.setScheduleNumber(vctinformation.getVctNumber());
		exportCargoVehicleDockInMessageEvent.setSource(vctinformation.getUserType());
		exportCargoVehicleDockInMessageEvent.setFunction("VCT Information");
		exportCargoVehicleDockInMessageEvent.setCreatedBy(vctinformation.getCreatedBy());
		exportCargoVehicleDockInMessageEvent.setCreatedOn(vctinformation.getCreatedOn());
		exportCargoVehicleDockInMessageEvent.setEventName(EventTypes.Names.Export_CARGO_VCT_EXP_SCHEDULE);

		
		if (vctinformation.getVctIn()) {
			exportCargoVehicleDockInMessageEvent.setAction(DOCK_IN);
			vctEXProducer.publish(exportCargoVehicleDockInMessageEvent);
		}
		
		
		 if(vctinformation.getVctOut()) {
			exportCargoVehicleDockInMessageEvent.setAction(DOCK_OUT);
			vctEXProducer.publish(exportCargoVehicleDockInMessageEvent);
		}
		
		}
		return vctinformationresponse;
	}

}
