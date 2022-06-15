/*package com.ngen.cosys.shipment.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.shipment.model.IrregularityDetail;
import com.ngen.cosys.shipment.model.IrregularitySummary;
import com.ngen.cosys.shipment.model.SearchShipmentIrregularity;
import com.ngen.cosys.shipment.service.MaintainShipmentIrregularityService;

*//**
 * This class is Unit Testing for Maintaining Shipment Irregularity Controller.
 * 
 * @author NIIT Technologies Ltd.
 *
 *//*
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class MaintainShipmentIrregularityControllerTest {

   @InjectMocks
   private MaintainShipmentIrregularityController maintainShipmentIrregularityController;
   
   @Mock
   private UtilitiesModelConfiguration utilitiesModelConfiguration;
   
   @Mock
   private MaintainShipmentIrregularityService maintainShipmentIrregularityService;
   
   @InjectMocks
   private BaseResponse baseResponse;
   
   @InjectMocks
   private IrregularitySummary maintainShipmentIrregularity; 
   
   @InjectMocks
   private IrregularityDetail irregularityDetails;

   @Before
   public void setup() {
      MockitoAnnotations.initMocks(this);
   }
   
   *//**
    * This method tests the fetch method of code share flight.
    * 
    * @throws SQLException
    * @throws CustomException
    *//*
   @Test
   public void testSearchShipment() throws SQLException, CustomException {
	   List<String> shc = new ArrayList<>();
	      shc.add("EAT");
	      shc.add("PER EAT");

      List<IrregularitySummary> searchList = new ArrayList<>();
      IrregularitySummary irregularity = new IrregularitySummary("123", "AWB", "123-456", "BLR", "DEL", 10, 100.0, "EATABLES", shc, null, 1);
      searchList.add(irregularity);
      
      SearchShipmentIrregularity search = new SearchShipmentIrregularity("AWB", "123", LocalDate.now());
      when(maintainShipmentIrregularityService.search(search)).thenReturn(irregularity);
      when(utilitiesModelConfiguration.getBaseResponseInstance()).thenReturn(baseResponse);
      
      BaseResponse<Object> result = maintainShipmentIrregularityController.searchShipment(search);
      assertNotNull(result.getData());
   }
      
}*/