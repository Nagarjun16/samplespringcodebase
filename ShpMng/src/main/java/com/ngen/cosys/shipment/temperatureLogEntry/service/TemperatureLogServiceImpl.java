package com.ngen.cosys.shipment.temperatureLogEntry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.temperatureLogEntry.dao.TemperatureLogEntryDAO;
import com.ngen.cosys.shipment.temperatureLogEntry.model.TemperatureLogEntry;
import com.ngen.cosys.shipment.temperatureLogEntry.model.TemperatureLogResponse;
import com.ngen.cosys.shipment.temperatureLogEntry.model.TemperatureSearch;

@Service
public class TemperatureLogServiceImpl implements TemperatureLogService {

   @Autowired
   private TemperatureLogEntryDAO temperatureLogEntryDAO;

   @Override
   public TemperatureLogResponse getTemperatureInfo(TemperatureSearch searchparam) throws CustomException {
      if (StringUtils.isEmpty(searchparam.getShipmentNumber())) {
         throw new CustomException("AWBEMPTYERROR", "ShipmentTemperatureSearchResponse", ErrorType.ERROR);
      }
      return temperatureLogEntryDAO.getShipmentInformation(searchparam);
   }

   @Override
   public List<TemperatureLogEntry> saveTemperatureEntry(List<TemperatureLogEntry> entrydata) throws CustomException {
      List<TemperatureLogEntry> retObj = temperatureLogEntryDAO.addTemperatureLogEntry(entrydata);
      return entrydata;
   }

   public List<TemperatureLogEntry> deleteTemperatureEntry(List<TemperatureLogEntry> entrydata) throws CustomException {
      List<TemperatureLogEntry> retObj = temperatureLogEntryDAO.deleteTemperatureLogEntry(entrydata);
      return entrydata;
   }
}