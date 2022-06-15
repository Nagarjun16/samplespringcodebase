/**
 * 
 * NeutralAWBControllerTest.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          1 February, 2018 NIIT      -
 *//*
package com.ngen.cosys.shipment.nawb.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
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
import com.ngen.cosys.shipment.nawb.model.SIDHeaderDetail;
import com.ngen.cosys.shipment.nawb.model.SearchSIDRQ;
import com.ngen.cosys.shipment.nawb.service.NeutralAWBService;

*//**
 * This class is Unit Testing for Neutral AWB Controller.
 * 
 * @author NIIT Technologies Ltd.
 *
 *//*
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class NeutralAWBControllerTest {

   @Mock
   private NeutralAWBService neutralAWBService;

   @InjectMocks
   private NeutralAWBController neutralAWBController;
   @InjectMocks
   private BaseResponse<SIDHeaderDetail> baseResponse;

   @Mock
   private UtilitiesModelConfiguration utilitiesModelConfiguration;
   private SIDHeaderDetail sidHeaderDetail;
   private SIDHeaderDetail sidHeaderDetail1;
   private List<SIDHeaderDetail> sidHeaderDetails;
   private final BigInteger sidHeaderId = new BigInteger("100135");
   private final String sidNumber = "20180111152908";
   private final String shipmentNumber = "11620906933";
   private final String shipperName = "CNE";
   private final String consigneeName = "Kaushlender";
   private final String status = "Created";
   private final String createdBy = "SYSADMIN";
   private final LocalDateTime createdOn = LocalDateTime.now().minusDays(2);
 
   @Before
   public void setup() {
      
      MockitoAnnotations.initMocks(this);
      sidHeaderDetail = new SIDHeaderDetail();
      sidHeaderDetail.setSidHeaderId(sidHeaderId);
      sidHeaderDetail.setSidNumber(sidNumber);
      sidHeaderDetail.setShipmentNumber(shipmentNumber);
      sidHeaderDetail.setShipperName(shipperName);
      sidHeaderDetail.setConsigneeName(consigneeName);
      sidHeaderDetail.setCreatedOn(createdOn);
      sidHeaderDetail.setStatus(status);
      sidHeaderDetail.setCreatedBy(createdBy);

      sidHeaderDetail1 = new SIDHeaderDetail();
      sidHeaderDetail1 = new SIDHeaderDetail();
      sidHeaderDetail1.setSidHeaderId(new BigInteger("100136"));
      sidHeaderDetail1.setSidNumber("20180111152909");
      sidHeaderDetail1.setShipmentNumber("11620906934");
      sidHeaderDetail1.setShipperName("Shyam");
      sidHeaderDetail1.setConsigneeName("CNE");
      sidHeaderDetail1.setCreatedOn(LocalDateTime.now().minusDays(3));
      sidHeaderDetail1.setStatus(status);
      sidHeaderDetail1.setCreatedBy(createdBy);

      sidHeaderDetails = new ArrayList<>();
      sidHeaderDetails.add(sidHeaderDetail);
       
   }

  // Case1 if Custom exception thrown by service searchSIDList method
   @Test(expected = CustomException.class)
   public void testsearchSIDListFailScenarioWithCustomException() throws CustomException {
        SearchSIDRQ searchSIDRQ = new SearchSIDRQ();
      searchSIDRQ.setSidNumber("20180111152908");
      when(neutralAWBService.searchSIDList(searchSIDRQ)).thenThrow(new CustomException());
      when(utilitiesModelConfiguration.getBaseResponseInstance()).thenReturn(baseResponse);
      assertEquals(new CustomException(), neutralAWBController.searchSIDList(searchSIDRQ));
       
   }
   
   // Case2 successful fetch of searchSIDList method By SidNumber Criteria
   @Test
   public void testfetchUserProfileListSuccessScenarioBySIDNumber() throws CustomException {
      SearchSIDRQ searchSIDRQ = new SearchSIDRQ();
      searchSIDRQ.setSidNumber("20180111152908");
      when(neutralAWBService.searchSIDList(searchSIDRQ)).thenReturn(sidHeaderDetails);
      when(utilitiesModelConfiguration.getBaseResponseInstance()).thenReturn(baseResponse);
      BaseResponse<List<SIDHeaderDetail>> result = neutralAWBController.searchSIDList(searchSIDRQ);
      assertNotNull(result.getData());
      assertEquals(consigneeName, result.getData().get(0).getConsigneeName());
      assertEquals(shipperName, result.getData().get(0).getShipperName());
      assertEquals(sidHeaderId, result.getData().get(0).getSidHeaderId());
      assertEquals(sidNumber, result.getData().get(0).getSidNumber());
      assertEquals(createdOn, result.getData().get(0).getCreatedOn());
      assertEquals(createdBy, result.getData().get(0).getCreatedBy());
      assertEquals(status, result.getData().get(0).getStatus());
   }

   // Case3 successful fetch of searchSIDList method without any Criteria
   @Test
   public void testfetchUserProfileListSuccessScenario() throws CustomException {
      SearchSIDRQ searchSIDRQ = new SearchSIDRQ();
      sidHeaderDetails.add(sidHeaderDetail1);
      when(neutralAWBService.searchSIDList(searchSIDRQ)).thenReturn(sidHeaderDetails);
      when(utilitiesModelConfiguration.getBaseResponseInstance()).thenReturn(baseResponse);
      BaseResponse<List<SIDHeaderDetail>> result = neutralAWBController.searchSIDList(searchSIDRQ);
      assertNotNull(result.getData());
      
      assertEquals(consigneeName, result.getData().get(0).getConsigneeName());
      assertEquals(shipperName, result.getData().get(0).getShipperName());
      assertEquals(sidHeaderId, result.getData().get(0).getSidHeaderId());
      assertEquals(sidNumber, result.getData().get(0).getSidNumber());
      assertEquals(createdOn, result.getData().get(0).getCreatedOn());
      assertEquals(createdBy, result.getData().get(0).getCreatedBy());
      assertEquals(status, result.getData().get(0).getStatus());

      assertEquals(sidHeaderDetail1.getConsigneeName(), result.getData().get(1).getConsigneeName());
      assertEquals(sidHeaderDetail1.getShipperName(), result.getData().get(1).getShipperName());
      assertEquals(sidHeaderDetail1.getSidHeaderId(), result.getData().get(1).getSidHeaderId());
      assertEquals(sidHeaderDetail1.getSidNumber(), result.getData().get(1).getSidNumber());
      assertEquals(sidHeaderDetail1.getCreatedOn(), result.getData().get(1).getCreatedOn());
      assertEquals(sidHeaderDetail1.getCreatedBy(), result.getData().get(1).getCreatedBy());
      assertEquals(sidHeaderDetail1.getStatus(), result.getData().get(1).getStatus());
   }
   
   // Case4 if null returned by service searchSIDList method
   @Test(expected = CustomException.class)
   public void testsearchSIDListFailScenarioWithNull() throws CustomException {
      SearchSIDRQ searchSIDRQ = new SearchSIDRQ();
      searchSIDRQ.setSidNumber("20180111152908");
      when(neutralAWBService.searchSIDList(searchSIDRQ)).thenReturn(null);
      when(utilitiesModelConfiguration.getBaseResponseInstance()).thenReturn(baseResponse);
      assertEquals(new CustomException("SID_FETCH_ERROR", "", "N"), neutralAWBController.searchSIDList(searchSIDRQ));
   }
}
*/