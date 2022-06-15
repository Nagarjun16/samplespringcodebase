package com.ngen.cosys.shipment.temperatureLogEntry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.shipment.temperatureLogEntry.model.TemperatureLogEntry;
import com.ngen.cosys.shipment.temperatureLogEntry.model.TemperatureLogEntryList;
import com.ngen.cosys.shipment.temperatureLogEntry.model.TemperatureLogResponse;
import com.ngen.cosys.shipment.temperatureLogEntry.model.TemperatureSearch;
import com.ngen.cosys.shipment.temperatureLogEntry.service.TemperatureLogService;

@NgenCosysAppInfraAnnotation
public class TemperatureLogEntryController {

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @Autowired
   private TemperatureLogService temperatureLogService;

   @RequestMapping(value = "/api/shipment/temperature/getmobile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<TemperatureLogResponse> shipmentTemperatureMobile(@RequestBody TemperatureSearch paramSearchLog)
         throws CustomException {
      BaseResponse<TemperatureLogResponse> data = utilitiesModelConfiguration.getBaseResponseInstance();
      TemperatureLogResponse res = temperatureLogService.getTemperatureInfo(paramSearchLog);
      data.setData(res);
      return data;
   }

   @RequestMapping(value = "/api/shipment/temperature/get", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public TemperatureLogResponse shipmentTemperature(@RequestBody TemperatureSearch paramSearchLog)
         throws CustomException {
      return temperatureLogService.getTemperatureInfo(paramSearchLog);
   }

   @RequestMapping(value = "/api/shipment/temperature/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public List<TemperatureLogEntry> shipmentTemperatureLogEntrySave(@RequestBody List<TemperatureLogEntry> entrydata)
         throws CustomException {
      return temperatureLogService.saveTemperatureEntry(entrydata);
   }

   @RequestMapping(value = "/api/shipment/temperature/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public List<TemperatureLogEntry> shipmentTemperatureLogEntryDelete(@RequestBody List<TemperatureLogEntry> entrydata)
         throws CustomException {
      return temperatureLogService.deleteTemperatureEntry(entrydata);
   }

   @RequestMapping(value = "/api/shipment/temperature/saveshipment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<TemperatureLogEntryList> shipmentTemperatureLogEntrySaveMobile(
         @RequestBody TemperatureLogEntryList entrydata) throws CustomException {
      BaseResponse<TemperatureLogEntryList> data = utilitiesModelConfiguration.getBaseResponseInstance();
      List<TemperatureLogEntry> res = entrydata.getTemperatureLogEntryList1();
      List<TemperatureLogEntry> result = temperatureLogService.saveTemperatureEntry(res);
      entrydata.setTemperatureLogEntryList1(result);
      data.setData(entrydata);
      return data;
   }
}