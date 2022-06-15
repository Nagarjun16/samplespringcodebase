package com.ngen.cosys.shipment.temperatureLogEntry.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.temperatureLogEntry.model.TemperatureLogEntry;
import com.ngen.cosys.shipment.temperatureLogEntry.model.TemperatureLogEntryList;
import com.ngen.cosys.shipment.temperatureLogEntry.model.TemperatureLogResponse;
import com.ngen.cosys.shipment.temperatureLogEntry.model.TemperatureSearch;

public interface TemperatureLogEntryDAO {
	/**
	 * 2. Method to get the temperature related details of an particular shipment based on the shipment number search
	 * 
	 * @param logEntryRequestModel
	 * @return TemperatureLogSearch
	 * @throws CustomException
	 */
	 TemperatureLogResponse getShipmentInformation(TemperatureSearch shipmentInformationRequestModel) throws CustomException;

	 List<TemperatureLogEntry> addTemperatureLogEntry(List<TemperatureLogEntry> entrydata) throws CustomException;

	 List<TemperatureLogEntry> deleteTemperatureLogEntry(List<TemperatureLogEntry> entrydata) throws CustomException;

}
