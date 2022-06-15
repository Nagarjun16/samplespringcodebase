package com.ngen.cosys.shipment.temperatureLogEntry.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.temperatureLogEntry.model.TemperatureLogEntry;
import com.ngen.cosys.shipment.temperatureLogEntry.model.TemperatureLogEntryList;
import com.ngen.cosys.shipment.temperatureLogEntry.model.TemperatureLogResponse;
import com.ngen.cosys.shipment.temperatureLogEntry.model.TemperatureSearch;

public interface TemperatureLogService {
   TemperatureLogResponse getTemperatureInfo(TemperatureSearch searchparam) throws CustomException;

   List<TemperatureLogEntry> saveTemperatureEntry(List<TemperatureLogEntry> entrydata) throws CustomException;

   List<TemperatureLogEntry> deleteTemperatureEntry(List<TemperatureLogEntry> entrydata) throws CustomException;
}