package com.ngen.cosys.application.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.application.model.TSMFreightoutModel;
import com.ngen.cosys.application.service.TSMFreightoutServiceJob;
import com.ngen.cosys.framework.exception.CustomException;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class TSMOutgoingFreightoutJobDummyController {

	@Autowired
	TSMFreightoutServiceJob service;

	private static final Logger LOGGER = LoggerFactory.getLogger(TSMOutgoingFreightoutJobDummyController.class);

	@ApiOperation("API for Testing TSM Freightout")
	@RequestMapping(value = "/api/satssgbatches/tsmoutgoingFreightout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void tsmOutgoingShipmentsDatatoFreightOut() throws CustomException {
		LOGGER.info("TSM Freight out job Started");
		try {
			// Get the list of shipments
			List<TSMFreightoutModel> shipments = this.service.getShipmentInfoForFreightOut();

			LOGGER.info("TSM Freight out job Shipments count" + shipments.size());

			if (!CollectionUtils.isEmpty(shipments)) {
				for (TSMFreightoutModel freightoutModel : shipments) {
					List<TSMFreightoutModel> inventoryList = this.service
							.getTSMOutgoingInventoryDetails(freightoutModel);
					if (!CollectionUtils.isEmpty(inventoryList)) {
						for (TSMFreightoutModel inventoryModel : inventoryList) {
							inventoryModel.setShipmentNumber(freightoutModel.getShipmentNumber());
							inventoryModel.setShipmentDate(freightoutModel.getShipmentDate());
							inventoryModel.setShipmentId(freightoutModel.getShipmentId());
							inventoryModel.setDataSyncShipmentId(freightoutModel.getDataSyncShipmentId());
							inventoryModel.setFlightId(freightoutModel.getFlightId());
							this.service.tsmInventoryToFreightOut(inventoryModel);
						}

					}
				}
			}
			LOGGER.info("TSM Freight out job END");
		} catch (CustomException ex) {
			LOGGER.error("Unable to  Autofreightout for this shipment", ex);
		}
	}

}
