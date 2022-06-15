/**
 * 
 * NeutralAWBServiceImplTest.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 1 February, 2018 NIIT -
 *//*
package com.ngen.cosys.shipment.nawb.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.nawb.dao.NeutralAWBDAO;
import com.ngen.cosys.shipment.nawb.model.SIDHeaderDetail;
import com.ngen.cosys.shipment.nawb.model.SearchSIDRQ;

*//**
 * This class is Unit Testing for NeutralAWBServiceImpl class.
 * 
 * @author NIIT Technologies Ltd.
 *
 *//*
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class NeutralAWBServiceImplTest {
   @Mock
   private NeutralAWBDAO neutralAWBDAO;
   @InjectMocks
   private NeutralAWBServiceImpl neutralAWBService;
   private SIDHeaderDetail sidHeaderDetail;
   private SIDHeaderDetail sidHeaderDetail1;
   private List<SIDHeaderDetail> sidHeaderDetails;
   private final BigInteger sidHeaderId = new BigInteger("100135");
   private final String sidNumber = "20180111152908";
   private final String shipmentNumber = "11620906933";
   private final String shipperName = "Kaushal";
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
      sidHeaderDetail1.setShipperName("Ram");
      sidHeaderDetail1.setConsigneeName("Shyam");
      sidHeaderDetail1.setCreatedOn(LocalDateTime.now().minusDays(3));
      sidHeaderDetail1.setStatus(status);
      sidHeaderDetail1.setCreatedBy(createdBy);

      sidHeaderDetails = new ArrayList<>();
      sidHeaderDetails.add(sidHeaderDetail);

   }

   // Case1 if Custom exception thrown by Dao searchSIDList method
   @Test(expected = CustomException.class)
   public void testSearchSIDListFailScenarioWithCustomException() throws CustomException {
      SearchSIDRQ searchSIDRQ = new SearchSIDRQ();
      searchSIDRQ.setSidNumber("20180111152908");
      when(neutralAWBDAO.searchSIDList(searchSIDRQ)).thenThrow(new CustomException());
      assertEquals(new CustomException(), neutralAWBService.searchSIDList(searchSIDRQ));
   }

   // Case2 if Null returned by Dao searchSIDList method
   @Test
   public void testSearchSIDListFailScenarioWithNull() throws CustomException {
      SearchSIDRQ searchSIDRQ = new SearchSIDRQ();
      searchSIDRQ.setSidNumber("20180111152908");
      when(neutralAWBDAO.searchSIDList(searchSIDRQ)).thenReturn(null);
      assertNull(null, neutralAWBService.searchSIDList(searchSIDRQ));
   }

   // Case3 Successful searchSIDList By SidNumber
   @Test
   public void testsearchSIDListBySIDNumberSuccessScenario() throws CustomException {
      SearchSIDRQ searchSIDRQ = new SearchSIDRQ();
      searchSIDRQ.setSidNumber("20180111152908");
      when(neutralAWBDAO.searchSIDList(searchSIDRQ)).thenReturn(sidHeaderDetails);
      assertEquals(sidHeaderDetails, neutralAWBService.searchSIDList(searchSIDRQ));
   }

}*/