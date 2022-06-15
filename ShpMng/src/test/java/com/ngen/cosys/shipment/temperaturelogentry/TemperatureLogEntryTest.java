/*package com.ngen.cosys.shipment.temperaturelogentry;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import com.ngen.cosys.shipment.temperatureLogEntry.controller.TemperatureLogEntryController;
import com.ngen.cosys.shipment.temperatureLogEntry.model.TemperatureLogEntry;
import com.ngen.cosys.shipment.temperatureLogEntry.model.TemperatureLogResponse;
import com.ngen.cosys.shipment.temperatureLogEntry.model.TemperatureSearch;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = { "/tddscripts/schema.sql",
              "/tddscripts/data.sql"
              })
 
public class TemperatureLogEntryTest {
   
   LocalDateTime ldt = LocalDateTime.now();
   private static final LocalDate LocalDate = null;
   @Autowired
   public TemperatureLogEntryController temperatureLogEntryController;
   
  @Test
   public void shipmentTemperatureLogEntrySave() throws CustomException {
     
   } 
   }
  
//   @Test
//   public void shipmentTemperatureLogEntrySave() throws CustomException {
//      TemperatureLogEntry temperaturelog=new TemperatureLogEntry();
//      List<TemperatureLogEntry> temperatureloglist = new ArrayList<TemperatureLogEntry>();
//      TemperatureLogResponse temperatureLogEntry=new TemperatureLogResponse();
//   temperaturelog.setActivity("play");
//    temperaturelog.setCapturedOn(ldt);
//    temperaturelog.setShipmentId(1138);
//   temperaturelog.setTemperature("2");
//   temperaturelog.setShipmentDescription("GHI");
////   temperaturelog.setShipmentTemperatureLogEntryId(41);
//temperaturelog.setLocationCode("UI");
//temperatureloglist.add(temperaturelog);
//      temperatureLogEntry.setTemperatureLogEntryData(temperatureloglist);
//      temperatureLogEntry.setShipmentId("1138");
//    
//     assertNotNull(temperatureLogEntryController.shipmentTemperatureLogEntrySave(temperatureLogEntry.getTemperatureLogEntryData()));
//   }
//   
//   @Test
//   public void shipmentTemperature() throws CustomException {
//      TemperatureSearch temperatureSearch=new TemperatureSearch();
//      temperatureSearch.setShipmentNumber("61255235224");
//      TemperatureLogResponse response =temperatureLogEntryController.shipmentTemperature(temperatureSearch);
//      TemperatureLogResponse data = response;
//      assertNotNull(data);
//  
//   }
//   
//   @Test
//   public void shipmentTemperatureLogEntryDelete() throws CustomException {
//      TemperatureLogEntry temperaturelog=new TemperatureLogEntry();
//      List<TemperatureLogEntry> temperatureloglist = new ArrayList<TemperatureLogEntry>();
//      TemperatureLogResponse temperatureLogEntry=new TemperatureLogResponse();
//   temperaturelog.setActivity("play");
//    temperaturelog.setCapturedOn(ldt);
//    temperaturelog.setShipmentId(1138);
//   temperaturelog.setTemperature("2");
//   temperaturelog.setShipmentDescription("GHI");
//   temperaturelog.setShipmentTemperatureLogEntryId(41);
//temperaturelog.setLocationCode("UI");
//temperatureloglist.add(temperaturelog);
//      temperatureLogEntry.setTemperatureLogEntryData(temperatureloglist);
//      temperatureLogEntry.setShipmentId("1138");
//    
//     assertNotNull(temperatureLogEntryController.shipmentTemperatureLogEntrySave(temperatureLogEntry.getTemperatureLogEntryData()));
//   }

*/