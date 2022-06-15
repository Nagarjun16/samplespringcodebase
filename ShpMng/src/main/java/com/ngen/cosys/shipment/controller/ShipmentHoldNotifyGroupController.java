package com.ngen.cosys.shipment.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.shipment.model.HoldNotifyShipment;
import com.ngen.cosys.shipment.model.SearchHoldNotifyShipment;
import com.ngen.cosys.shipment.model.UpdateHoldNotifyGroup;
import com.ngen.cosys.shipment.model.UpdateHoldNotifyShipments;
import com.ngen.cosys.shipment.service.ShipmentHoldNotifyGroupService;

@NgenCosysAppInfraAnnotation
public class ShipmentHoldNotifyGroupController {

	private static final String EXCEPTION = "Exception Happened ... ";

	private static final Logger lOgger = LoggerFactory.getLogger(ShipmentOnHoldController.class);

	@Autowired
	private ShipmentHoldNotifyGroupService shipmentHoldNotifyGroupService;

	@Autowired
	private UtilitiesModelConfiguration utilitiesModelConfiguration;

	/**
	 * Searching All shipment details
	 * 
	 * @param shipmentId
	 * @throws CustomException
	 */
	@RequestMapping(value = "/api/shipment/holdshipmentnotifygroup/fetch", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<List<HoldNotifyShipment>> fetchHoldNotifyShipmentList(
			@RequestBody @Valid SearchHoldNotifyShipment search) throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<List<HoldNotifyShipment>> shipmentOnHoldListResponse = utilitiesModelConfiguration
				.getBaseResponseInstance();

		if (search.getHoldNotifyGroup() != null || search.getShipmentNumber() != null) {
			shipmentOnHoldListResponse.setData(shipmentHoldNotifyGroupService.onSearch(search));
		}
		return shipmentOnHoldListResponse;
	}

	@RequestMapping(value = "/api/shipment/holdshipmentnotifygroup/updatehold", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean updateHoldNotifyShipments(@RequestBody @Valid UpdateHoldNotifyShipments updateHold)
			throws CustomException {

		if (!updateHold.getShipmentNumber().isEmpty()) {
			return shipmentHoldNotifyGroupService.updateHold(updateHold);
		}

		return false;
	}

	@RequestMapping(value = "/api/shipment/holdnotifygroup/updateholdnotifygroup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean updateHoldNotifyGroup(@RequestBody @Valid UpdateHoldNotifyGroup updateHoldNotifyGroup)
			throws CustomException {

		if (!updateHoldNotifyGroup.getShipmentNumber().isEmpty()
				&& updateHoldNotifyGroup.getHoldNotifyGroup() != null) {
			return shipmentHoldNotifyGroupService.updateHoldNotifyGroup(updateHoldNotifyGroup);
		}
		return false;
	}

	@RequestMapping(value = "/api/shipment/holdnotifygroup/updateack", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean updateAck(@RequestBody @Valid UpdateHoldNotifyShipments updateAck) throws CustomException {
		if (updateAck.getShipmentInventoryId() != null && !updateAck.getShipmentInventoryId().isEmpty()) {
			return shipmentHoldNotifyGroupService.updateAck(updateAck);
		}
		return false;
	}

}
